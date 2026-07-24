package com.triteam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@Data
@NoArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferId;

    @Column(name = "from_locatio_id")
    private Location fromLocation;

    @Column(name = "to_location_id")
    private Location toLocation;

    @Column(name = "product_id")
    private Product product;

    private Integer quantity;
    private String status;
    private LocalDateTime transferDate;
    private String notes;
}
