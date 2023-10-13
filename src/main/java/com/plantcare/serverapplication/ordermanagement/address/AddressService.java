package com.plantcare.serverapplication.ordermanagement.address;

public interface AddressService {
    AddressDto getAllShippingAddressesByAdminTransactions();
    AddressDto getAllBillingAddressesByAdminTransactions();
}
