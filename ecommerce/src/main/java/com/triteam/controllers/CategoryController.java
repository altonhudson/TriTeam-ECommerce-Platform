package com.triteam.controllers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.triteam.entity.Product;
import com.triteam.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryController {
    // Inject the database repository
    private final ProductRepository productRepository;

    public CategoryController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @GetMapping("/category")
    public String getCategory(
            @RequestParam(required = false) String param,
            @RequestParam(required = false, defaultValue = "all") String category,
            @RequestParam(required = false, defaultValue = "2000") Double maxPrice,
            @RequestParam(required = false, defaultValue = "name_asc") String sort,
            @RequestParam(required = false, defaultValue = "0") int page,
            Model model) {

        // Determine the active category
        String activeCategory = (param != null && !param.isEmpty()) ? param : category;

        // Applying Sorting
        org.springframework.data.domain.Sort jpaSort;
        if ("name_asc".equals(sort)) {
            // Sorts list alphabetically (A to Z)
            jpaSort = org.springframework.data.domain.Sort.by("name").ascending();
        } else if ("name_desc".equals(sort)) {
            // Sorts list in reverse alphabetical order (Z to A)
            jpaSort = org.springframework.data.domain.Sort.by("name").descending();
        } else if ("price_asc".equals(sort)) {
            // Sorts the list by price from lowest to highest
            jpaSort = org.springframework.data.domain.Sort.by("unitPrice").ascending();
        } else if ("price_desc".equals(sort)) {
            // Sorts the list by price from highest to lowest
            jpaSort = org.springframework.data.domain.Sort.by("unitPrice").descending();
        } else {
            jpaSort = org.springframework.data.domain.Sort.by("name").ascending();
        }

        // Create a Pageable object: requesting a specific page, 6 items per page, applying our sort
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, 6, jpaSort);

        // Applying Filters (Category and Max Price) from the URL Parameter
        org.springframework.data.domain.Page<Product> productPage;
        if ("all".equalsIgnoreCase(activeCategory)) {
            productPage = productRepository.findByUnitPriceLessThanEqual(maxPrice, pageable);
        } else {
            productPage = productRepository.findByCategoryAndUnitPriceLessThanEqual(activeCategory, maxPrice, pageable);
        }

        // Passing the filtered list to the Thymeleaf frontend html template under "products"
        model.addAttribute("products", productPage.getContent()); // .getContent() extracts the List from the Page

        // Passing pagination details to the view
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        // Passing the currently active filter states back to the view so the sidebar remembers them
        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("currentMaxPrice", maxPrice);
        model.addAttribute("currentSort", sort);

        return "category";
    }

}
