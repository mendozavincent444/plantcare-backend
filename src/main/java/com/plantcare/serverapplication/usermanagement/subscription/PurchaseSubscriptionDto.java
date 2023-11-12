package com.plantcare.serverapplication.usermanagement.subscription;

import com.plantcare.serverapplication.ordermanagement.address.AddressDto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PurchaseSubscriptionDto {
    private int subscriptionTypeId;
    private BigDecimal price;
    private AddressDto billingAddressDto;
    private String paymentMethod;
}
