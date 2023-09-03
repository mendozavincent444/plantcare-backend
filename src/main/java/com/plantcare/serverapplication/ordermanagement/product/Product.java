package com.plantcare.serverapplication.ordermanagement.product;

import jakarta.persistence.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "units_in_stock", nullable = false)
    private int unitsInStock;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;
}
