package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.config.CustomUserDetails;
import com.ims.inventorymgmtsys.config.MfaAuthentication;
import com.ims.inventorymgmtsys.config.MfaAuthenticationCodeVerifier;
import com.ims.inventorymgmtsys.config.QrCode;
import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.service.MfaService;
import com.ims.inventorymgmtsys.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class MfaFactorAuthController {
    private final UserService userService;

    private final MfaAuthenticationCodeVerifier codeVerifier;

    private final QrCode qrCode;

    private final MfaService mfaService;

    private final AuthenticationSuccessHandler successHandler;

    private final AuthenticationFailureHandler failureHandler;

    public MfaFactorAuthController(UserService userService, MfaAuthenticationCodeVerifier codeVerifier, QrCode qrCode, MfaService mfaService, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        this.userService = userService;
        this.codeVerifier = codeVerifier;
        this.qrCode = qrCode;
        this.mfaService = mfaService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }
    @GetMapping("/mfa-enable-disable")
    public String enableDisableMfa(Model model){
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "auth/mfaEnableDisable";
    }

    @GetMapping("/enable-2fa")
    public String requestEnableMfa(Model model) {
        User user = userService.getCurrentUser();
        if (user.getSecret() == null) {
            String secret = mfaService.generateSecretKey();
            user.setSecret(secret);
            userService.updateSecret(user);
        }
        String otpAuthUrl = "otpauth://totp/%s?secret=%s&digits=6".formatted("IMS: " + user.getUserName(), user.getSecret());
        System.out.println("Secret: " + user.getSecret());
        System.out.println("QR Code Data URL: " + this.qrCode.dataUrl(otpAuthUrl));
        model.addAttribute("qrCode", this.qrCode.dataUrl(otpAuthUrl));
        model.addAttribute("secret", user.getSecret());
        return "auth/enable-2fa";
    }

    @PostMapping("/enable-2fa")
    public String processEnableMfa(@RequestParam String code, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = userService.getCurrentUser();
        if (user.getMfaEnabled()) {
            return "redirect:/";
        }
        if (!this.codeVerifier.verify(user, code)) {
            model.addAttribute("message", "Invalid code");
            return this.requestEnableMfa(model);
        }
        System.out.println("Received code::::::::::::: " + code);
        System.out.println("Current user::::::::::::::: " + user);

        User enabled = userService.updateMfa(user);
        User updatedUser = userService.getCurrentUser();
        Authentication token = UsernamePasswordAuthenticationToken.authenticated(new CustomUserDetails(updatedUser), null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        model.addAttribute("user", updatedUser);

        System.out.println("MFA Enabled: " + updatedUser.getMfaEnabled());
        return "auth/mfaEnableDisable";
    }

    @GetMapping("/challenge/totp")
    public String requestTotp() {
        return "auth/totp";
    }

//    @PostMapping("/challenge/totp")
//    public void processTotp(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (currentAuthentication instanceof MfaAuthentication mfaAuth) {
//            User user = userService.getCurrentUser();
//            if (user != null && codeVerifier.verify(user, code)) {
//                Authentication newAuth = new MfaAuthentication(
//                        new UsernamePasswordAuthenticationToken(
//                        mfaAuth.getPrincipal(),
//                        mfaAuth.getCredentials(),
//                        mfaAuth.getAuthorities()
//                        ),
//                        userService
//                );
//                newAuth.setAuthenticated(true);
//                SecurityContextHolder.getContext().setAuthentication(newAuth);
//                successHandler.onAuthenticationSuccess(request, response, newAuth);
//            } else {
//                failureHandler.onAuthenticationFailure(request, response, new BadCredentialsException("Invalid code"));
//            }
//        } else {
//            System.out.println("Current Authentication is not of type MfaAuthentication: " + currentAuthentication);
//            throw new IllegalStateException("Current user principal is not of type MfaAuthentication");
//        }
//
//    }
@PostMapping("/challenge/totp")
public void processTotp(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // 現在の認証情報が MfaAuthentication 型である場合
    if (auth instanceof MfaAuthentication) {
        MfaAuthentication mfaAuth = (MfaAuthentication) auth;

        // TOTP コードの検証処理
        User user = userService.getCurrentUser();
        if (user != null && codeVerifier.verify(user, code)) {
            // 認証成功時の処理
            Authentication newAuth = new MfaAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            mfaAuth.getPrincipal(),
                            mfaAuth.getCredentials(),
                            mfaAuth.getAuthorities()
                    ),
                    userService
            );
            newAuth.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            successHandler.onAuthenticationSuccess(request, response, newAuth);
        } else {
            // 認証失敗時の処理
            failureHandler.onAuthenticationFailure(request, response, new BadCredentialsException("Invalid code"));
        }
    }
    // 現在の認証情報が UsernamePasswordAuthenticationToken 型である場合
    else if (auth instanceof UsernamePasswordAuthenticationToken) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        // TOTP コードの検証処理
        User user = userService.getCurrentUser();
        if (user != null && codeVerifier.verify(user, code)) {
            // 認証成功時の処理
            Authentication newAuth = new MfaAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            auth.getCredentials(),
                            auth.getAuthorities()
                    ),
                    userService
            );
            newAuth.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            successHandler.onAuthenticationSuccess(request, response, newAuth);
        } else {
            // 認証失敗時の処理
            failureHandler.onAuthenticationFailure(request, response, new BadCredentialsException("Invalid code"));
        }
    }
    // 予期しない認証情報型の場合
    else {
        throw new IllegalStateException("Current user principal is not of expected type");
    }
}


}
