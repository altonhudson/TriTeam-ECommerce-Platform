package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.triteam.entity.Product;
import com.triteam.repository.InventoryRepository;
import com.triteam.repository.ProductRepository;
import com.triteam.repository.SupplierRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminProductController {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final InventoryRepository inventoryRepository;

    public AdminProductController(ProductRepository productRepository, SupplierRepository supplierRepository,
            InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/admin/product/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "add-Product";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("suppliers", supplierRepository.findAll());
            return "add-Product";
        }
        productRepository.save(product);

        return "redirect:/category";
    }

}
