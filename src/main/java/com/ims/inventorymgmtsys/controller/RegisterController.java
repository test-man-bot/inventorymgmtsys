package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() { return "auth/register"; }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute User user) {
        User existsUser = userService.selectById(user.getId());
        if (existsUser != null) {
            return "auth/register";
        }
        userService.createUser(user);
        return "auth/registerCompletion";
    }


}
