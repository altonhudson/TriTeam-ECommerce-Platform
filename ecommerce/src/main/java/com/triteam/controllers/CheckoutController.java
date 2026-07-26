package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.triteam.entity.Product;
import com.triteam.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckoutController {
    private final ProductRepository productRepository;

    public CheckoutController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/checkout")
    public String getCartDetails(@RequestParam(required = false, defaultValue = "view") String param, HttpSession session, Model model) {
        // Retrieving the cart from the session
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Price calculations for the checkout view
        double subtotal = cart.stream().mapToDouble(Product::getUnitPrice).sum();
        double tax = subtotal * 0.13;
        double total = subtotal + tax;

        // passing everything to the checkout.html template
        model.addAttribute("cartItems", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("tax", tax);
        model.addAttribute("total", total);

        // displays items added to cart with subtotal/taxes/shipping/etc.
        return "checkout";
    }

    // Route for adding an item to the cart
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, HttpSession session, HttpServletRequest request) {
        // Retrieve the cart from the session
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        // Add the product
        productRepository.findById(productId).ifPresent(cart::add);
        session.setAttribute("cart", cart);
        
        // Retrieve current URL (including the filter URL params)
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    // Route for removing an item from the cart
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        // Retrieve the cart from the session
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        
        if (cart != null) {
            // Iterate through the cart product list and remove the first instance of the product
            for (int i = 0; i < cart.size(); i++) {
                if (cart.get(i).getProductId().equals(productId)) {
                    cart.remove(i);
                    break; // Stop after removing one
                }
            }
            // Save the updated list back to the session
            session.setAttribute("cart", cart);
        }
        
        // redirecting back to the checkout page to display their updated cart/total
        return "redirect:/checkout";
    }
}
