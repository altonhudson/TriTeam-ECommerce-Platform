package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    // service declaration

    @GetMapping("/home")
    public String getProductCatalog(@RequestParam String param) {
        // home page provides filterable/searchable list view of products catalogued in
        // database
        return "home";
    }

}
