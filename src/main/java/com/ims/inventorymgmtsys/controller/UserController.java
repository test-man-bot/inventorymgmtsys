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
//
//    @GetMapping("profile")
//    public String getUserProfile (Model model) {
//        User user = new User();
//        model.addAttribute("userProfile", user);
//        return "user/profile";
//    }
//
//    @PostMapping("update-user")
//    public String updateUserProfile(@Validated @ModelAttribute("userProfile") User user, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "user/profile";
//        }
//
//        User updateUser = userService.selectById(user.getId());
//
//        if (updateUser == null) {
//            bindingResult.rejectValue("id","error.id", "Invalid user selected");
//            model.addAttribute("user", userService.selectById(userService.getCurrentId()));
//            return "user/profile";
//        }
//        updateUser.setId(user.getId());
//        updateUser.setUserName(user.getUserName());
//        updateUser.setEmailAddress(user.getEmailAddress());
//        updateUser.setAddress(user.getAddress());
//        updateUser.setPhone(user.getPhone());
//        boolean isUpdate;
//        try {
//            isUpdate = userService.updateUser(updateUser);
//            if (isUpdate) {
//                model.addAttribute("successMessage", "更新しました");
//            } else {
//                model.addAttribute("successMessage", "更新はありません");
//            }
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "更新中にエラーが発生しました: " + e.getMessage());
//        }
//
//        model.addAttribute("user", updateUser);
//        return "user/profile";
//    }

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
