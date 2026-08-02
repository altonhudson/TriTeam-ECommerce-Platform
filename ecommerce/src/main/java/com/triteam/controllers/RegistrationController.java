package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.triteam.model.User;
import com.triteam.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        user.setRole("ROLE_USER");
        userService.saveUser(user);

        return "redirect:/login?registered";
    }

}
