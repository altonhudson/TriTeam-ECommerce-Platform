package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {
    // service declaration

    @GetMapping("/login")
    public String getLoginAuth(@RequestParam String param) {
        // redirects from any page if login auth is invalid
        return "login";
    }

    @PostMapping("/login")
    public String postMethodName(@RequestBody String entity) {
        // TODO: process POST request
        boolean i = false;
        if (i = true)// login auth
        {
            return "home";
        } else
            return "login";
    }

}
