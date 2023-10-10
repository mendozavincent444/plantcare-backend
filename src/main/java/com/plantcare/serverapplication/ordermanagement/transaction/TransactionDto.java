package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.ordermanagement.orderitem.OrderItemDto;
import com.plantcare.serverapplication.shared.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class TransactionDto {
    private int id;
    private Date date;
    private String name;
    private String description;
    private BigDecimal amount;
    private String status;
    private String paymentMethod;
    private List<OrderItemDto> orderItems;
    private String orderedBy;
}
