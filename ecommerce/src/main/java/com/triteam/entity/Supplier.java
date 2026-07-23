package com.triteam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "supplier")
@Data
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String companyName;
    private String contactName;
    private String contactEmail;
    private String phone;
    private Integer leadTimeDays;
    private Integer minOrderQty;
}
