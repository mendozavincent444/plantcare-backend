package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.ordermanagement.address.AddressDto;
import com.plantcare.serverapplication.ordermanagement.orderitem.OrderItemDto;
import lombok.Getter;

import java.util.List;
@Getter
public class PurchaseDto {
    List<OrderItemDto> orderItems;
    AddressDto shippingAddressDto;
    AddressDto billingAddressDto;
    String paymentMethod;
}
