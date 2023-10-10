package com.plantcare.serverapplication.ordermanagement.orderitem;

import com.plantcare.serverapplication.ordermanagement.product.ProductDto;
import lombok.Getter;

import java.math.BigDecimal;
@Getter
public class OrderItemDto {
    private int id;
    private int quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
