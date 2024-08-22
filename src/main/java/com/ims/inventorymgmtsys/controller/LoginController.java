package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.service.PasswordResetService;
import com.ims.inventorymgmtsys.service.UserService;
import net.snowflake.client.jdbc.internal.grpc.xds.shaded.io.envoyproxy.envoy.config.accesslog.v3.ComparisonFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserService userService;
    private final PasswordResetService passwordResetService;

    public LoginController (UserService userService, PasswordResetService passwordResetService) {
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }

    @RequestMapping("/access-denied")
    public String accessDenied() { return "auth/accessDenial"; }

    @GetMapping("/login")
    public String loginForm() { return "auth/loginForm"; }


    @GetMapping(value = "/login", params = "failure")
    public String loginFail(Model model) {
        model.addAttribute("failureMessage", "IDもしくはパスワードが違います");
        return "auth/loginForm";
    }


    @GetMapping("/forget")
    public String forgetPasswordForm() { return "auth/passwordForgetForm"; }

    @PostMapping("/password")
    public String forgetPasswordReset(@RequestParam("emailAddress") String emailAddress, Model model) {
        if (emailAddress == null || userService.findByEmail(emailAddress).isEmpty() ) {
            model.addAttribute("errorMessage", "メールアドレスは存在しませんでした");
            return "auth/passwordForgetForm";
        }

        Optional<User> user = userService.findByEmail(emailAddress);

        if ( user.isPresent() ) {
            passwordResetService.createToken(emailAddress);
            model.addAttribute("successMessage", "リンクを送信しました。");
        } else {
            model.addAttribute("errorMessage", "リンクの送信に失敗しました。");
        }

        return "auth/passwordForgetForm";
    }


}
