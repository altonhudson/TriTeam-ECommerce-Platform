package com.triteam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "location_id")
    private Long locationId;

    private Integer qtyOnHand;
    private LocalDateTime lastUpdated;
    private Integer reorderPoint;
    private Integer reorderQty;
}

