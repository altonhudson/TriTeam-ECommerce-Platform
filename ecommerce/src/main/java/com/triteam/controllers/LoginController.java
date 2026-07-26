package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    // service declaration

    @GetMapping("/login")
    public String getLoginAuth() {
        // redirects from any page if login auth is invalid
        return "login";
    }
}
