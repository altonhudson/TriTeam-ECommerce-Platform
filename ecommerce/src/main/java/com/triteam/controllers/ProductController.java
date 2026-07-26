package com.triteam.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.triteam.entity.Product;
import com.triteam.repository.ProductRepository;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Display Form
    @GetMapping("/admin/product/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product()); // Passing an empty Product object to the form so the inputs are bound
        return "add-product";
    }

    // Processing the Form
    @PostMapping("/admin/product/add")
    public String addProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        // If validation fails, return the form so the user can see the error messages
        if (bindingResult.hasErrors()) {
            return "add-product";
        }
        
        // If successful, save to the db and redirect to the category page to display the new item
        productRepository.save(product);
        return "redirect:/category";
    }
}
