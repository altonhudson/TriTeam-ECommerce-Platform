package com.triteam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String orderNumber;
    private String status;
    private LocalDateTime orderDate;
    private Double taxAmount;
    private Double shippingCost;
    private String shippingAddress;
    private String trackingNumber;
    private Double totalAmount;
    private String notes;
}
