package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {
    // service declaration

    @GetMapping("/category")
    public String getMethodName(@RequestParam String param) {
        // category choice is passed to page as parameter and filters list view to
        // display only items from the selected category consider masking endpoint with
        // selected category i.e. "/stationary" instead of "/category"
        return "category";
    }

}
