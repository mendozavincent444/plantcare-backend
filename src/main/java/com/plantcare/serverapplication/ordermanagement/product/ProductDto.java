package com.plantcare.serverapplication.ordermanagement.product;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String imageUrl;
    private int quantity;
}
