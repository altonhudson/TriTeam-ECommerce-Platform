package com.triteam.service;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.triteam.entity.Product;
import com.triteam.repository.ProductRepository;

@Service
public class SkuService {
    // Maps each category to its SKU prefix 
    // (following the format used in data.sql dev build product seedscript)
    private static final Map<String, String> CATEGORY_PREFIXES = Map.of(
            "computers", "COMP",
            "smartphones", "PHONE",
            "audio", "AUD",
            "accessories", "ACC",
            "networking", "NET",
            "office", "OFF");

    private final ProductRepository productRepository;

    public SkuService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Generate the next SKU for a prospective new product given its category
    // e.g. the third computer product would be  COMP-003
    public String generateSku(String category) {
        String prefix = CATEGORY_PREFIXES.getOrDefault(category == null ? "" : category.toLowerCase(), "GEN");
        int nextNumber = 1;
        Optional<Product> latest = productRepository.findFirstBySkuStartingWithOrderBySkuDesc(prefix + "-");
        if (latest.isPresent()) {
            String lastSku = latest.get().getSku();
            try {
                nextNumber = Integer.parseInt(lastSku.substring(prefix.length() + 1)) + 1;
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                // old SKU didn't match the expected format so start over
                nextNumber = 1;
            }
        }
        return String.format("%s-%03d", prefix, nextNumber);
    }
}