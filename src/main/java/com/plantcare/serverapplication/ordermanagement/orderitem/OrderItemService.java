package com.plantcare.serverapplication.ordermanagement.orderitem;

import java.util.List;

public interface OrderItemService {

    List<OrderItemDto> convertListToDto(List<OrderItem> orderItems);
}
