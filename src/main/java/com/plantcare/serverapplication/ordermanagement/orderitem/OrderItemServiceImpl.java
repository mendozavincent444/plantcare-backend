package com.plantcare.serverapplication.ordermanagement.orderitem;

import com.plantcare.serverapplication.ordermanagement.product.ProductService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final ProductService productService;

    public OrderItemServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public OrderItemDto convertToDto(OrderItem orderItem) {
        return OrderItemDto
                .builder()
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .product(this.productService.convertToDto(orderItem.getProduct()))
                .build();
    }
}
