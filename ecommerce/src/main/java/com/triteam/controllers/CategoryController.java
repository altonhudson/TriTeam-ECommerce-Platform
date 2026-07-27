package com.triteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.triteam.entity.Product;
import com.triteam.repository.ProductRepository;

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
            @RequestParam(required = false, defaultValue = "") String keyword, // search parameter
            Model model) {

        // Determine the active category
        String activeCategory = (param != null && !param.isEmpty()) ? param : category;

        org.springframework.data.domain.Sort jpaSort;

        if ("name_asc".equals(sort)) {
            jpaSort = org.springframework.data.domain.Sort.by(Product::getName).ascending();
        } else if ("name_desc".equals(sort)) {
            jpaSort = org.springframework.data.domain.Sort.by(Product::getName).descending();
        } else if ("price_asc".equals(sort)) {
            jpaSort = org.springframework.data.domain.Sort.by(Product::getUnitPrice).ascending();
        } else if ("price_desc".equals(sort)) {
            jpaSort = org.springframework.data.domain.Sort.by(Product::getUnitPrice).descending();
        } else {
            jpaSort = org.springframework.data.domain.Sort.by(Product::getName).ascending();
        }

        // Create a Pageable object: requesting a specific page 6 items per page,
        // applying our sort
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, 6, jpaSort);

        // Applying Filters (Category, Max Price and Keyword) from the URL Parameter
        org.springframework.data.domain.Page<Product> productPage;
        if ("all".equalsIgnoreCase(activeCategory)) {
            productPage = productRepository.findByUnitPriceLessThanEqualAndNameContainingIgnoreCase(maxPrice, keyword, pageable);
        } else {
            productPage = productRepository.findByCategoryAndUnitPriceLessThanEqualAndNameContainingIgnoreCase(activeCategory, maxPrice, keyword, pageable);
        }

        // Passing the filtered list to the Thymeleaf frontend html template under
        // "products"
        model.addAttribute("products", productPage.getContent()); // .getContent() extracts the List from the Page

        // Passing pagination details to the view
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        // Passing the currently active filter states back to the view so the sidebar
        // remembers them
        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("currentMaxPrice", maxPrice);
        model.addAttribute("currentSort", sort);
        model.addAttribute("keyword", keyword);

        return "category";
    }

}
