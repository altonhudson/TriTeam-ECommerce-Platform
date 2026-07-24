package com.triteam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(name = "order_id")
    private Order order;

    @Column(name = "product_id")
    private Product product;

    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
}
