package com.plantcare.serverapplication.ordermanagement.address;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {
    private String city;
    private String province;
    private String street;
    private String zipCode;
}
