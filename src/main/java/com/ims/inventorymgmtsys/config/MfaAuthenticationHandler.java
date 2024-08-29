package com.ims.inventorymgmtsys.config;

import java.io.IOException;

import com.ims.inventorymgmtsys.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

public class MfaAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final AuthenticationSuccessHandler successHandler;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private final UserService userService;

    public MfaAuthenticationHandler(String successUrl, UserService userService) {
        this.userService = userService;
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler(successUrl);
        successHandler.setAlwaysUseDefaultTargetUrl(true);
        this.successHandler = successHandler;

    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        Authentication anonymous = new AnonymousAuthenticationToken("key", "anonymousUser",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        saveMfaAuthentication(request, response, new MfaAuthentication(anonymous,userService));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        saveMfaAuthentication(request, response, authentication);
    }

    private void saveMfaAuthentication(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new MfaAuthentication(authentication, userService));
        this.securityContextRepository.saveContext(context, request, response);
        this.successHandler.onAuthenticationSuccess(request, response, authentication);
    }

}
