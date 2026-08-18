package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.triteam.entity.Product;
import com.triteam.repository.ProductRepository;
import com.triteam.service.CartService;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;

    public CartController(CartService cartService, ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
    }

    @GetMapping("/checkout")
    public String getCartDetails(@RequestParam(required = false, defaultValue = "view") String param,
            HttpSession session, Model model) {
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

    @PostMapping("/order/confirm")
    public String confirmOrder(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) String paymentMethod,
            HttpSession session) {

        List<Product> cart = (List<Product>) session.getAttribute("cart");
        double finalTotal = 0.0;

        if (cart != null && !cart.isEmpty()) {
            double subtotal = cart.stream().mapToDouble(Product::getUnitPrice).sum();
            double tax = subtotal * 0.13;
            finalTotal = subtotal + tax;
        }
        // save order to database
        cartService.confirmOrder(fullName, phone, address, city, postalCode, paymentMethod, finalTotal);

        // Clear the cart from the session after successful checkout
        session.removeAttribute("cart");

        // Return the order-success.html template
        return "order-success";
    }

    // Route for adding an item to the cart
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, HttpSession session, HttpServletRequest request) {
        // 1. Persist to database via your CartService
        cartService.addProductToCart(productId);

        // 2. Keep teammate's session logic synchronized for checkout views
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        productRepository.findById(productId).ifPresent(cart::add);
        session.setAttribute("cart", cart);

        // 3. Redirect back to wherever the user was originally (Home, Category, etc.)
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/home");
    }

    // Route for removing an item from the cart
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        // Retrieve the cart from the session
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart != null) {
            // Iterate through the cart product list and remove the first instance of the
            // product
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