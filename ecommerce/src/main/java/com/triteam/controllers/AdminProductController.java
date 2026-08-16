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
import com.triteam.service.SkuService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminProductController {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final InventoryRepository inventoryRepository; // currently saves new products to the product repo but
                                                           // doesn't save to the inventory repo, consider an ADMIN
                                                           // action to update inventory repo?

    private final SkuService skuService;

    public AdminProductController(ProductRepository productRepository, SupplierRepository supplierRepository, InventoryRepository inventoryRepository, SkuService skuService) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.skuService = skuService;
    }

    @GetMapping("/staff/product/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "add-Product";
    }

    // Returns the auto-generated SKU for a category to give a live preview on the form in the frontend
    @GetMapping("/staff/product/next-sku")
    @ResponseBody
    public String previewNextSku(@RequestParam String category) {
        return skuService.generateSku(category);
    }

    @PostMapping("/staff/product/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("suppliers", supplierRepository.findAll());
            return "add-Product";
        }
        product.setSku(skuService.generateSku(product.getCategory())); // set SKU
        productRepository.save(product);

        return "redirect:/category";
    }

}
