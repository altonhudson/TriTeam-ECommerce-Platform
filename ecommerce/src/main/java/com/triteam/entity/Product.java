package com.triteam.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    // apply a unique constraint when table is generated to ensure no two rows are the same
    @Column(unique = true)
    private String sku;

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotNull(message = "Price is mandatory")
    @Min(value = 1, message = "Price must be at least $1.00")
    @Max(value = 10000, message = "Price can't exceed $10,000")
    private Double unitPrice;

    // Can be computers, smartphones, audio, accessories, networking or office supplies
    @NotBlank(message = "A category is mandatory")
    @Pattern(regexp = "^(computers|smartphones|audio|accessories|networking|office)$", message = "Invalid category selected")
    private String category;
;
    private Double weightKg;
    private String description;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
