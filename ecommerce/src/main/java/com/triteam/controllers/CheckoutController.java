package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public String getCartDetails(@RequestParam String param) {
        // displays items added to cart with subtotal/taxes/shipping/etc.
        return "checkout";
    }

}
