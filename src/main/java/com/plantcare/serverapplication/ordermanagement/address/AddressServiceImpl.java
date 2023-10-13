package com.plantcare.serverapplication.ordermanagement.address;

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // use inner join to get all shipping addresses of the admin making the request
    @Override
    public AddressDto getAllShippingAddressesByAdminTransactions() {
        return null;
    }

    // use inner join to get all billing addresses of the admin making the request

    @Override
    public AddressDto getAllBillingAddressesByAdminTransactions() {
        return null;
    }
}
