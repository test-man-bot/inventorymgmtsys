package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.service.UserService;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public  UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        // 現在のユーザー情報を取得
        User user = userService.getCurrentUser();
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "user/profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("userProfile") User userProfile, Model model) {
        try {
            userService.updateUserProfile(userProfile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "更新に失敗しました" + e.getMessage());
        }
        User user = userService.getCurrentUser();
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("successMessage", "更新しました");
        return "user/profile";
    }

}
