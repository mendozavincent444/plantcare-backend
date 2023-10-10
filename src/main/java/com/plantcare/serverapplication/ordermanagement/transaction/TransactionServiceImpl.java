package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.ordermanagement.address.Address;
import com.plantcare.serverapplication.ordermanagement.address.AddressDto;
import com.plantcare.serverapplication.ordermanagement.address.AddressRepository;
import com.plantcare.serverapplication.ordermanagement.orderitem.OrderItem;
import com.plantcare.serverapplication.ordermanagement.orderitem.OrderItemDto;
import com.plantcare.serverapplication.ordermanagement.product.ProductService;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AddressRepository addressRepository,
            UserRepository userRepository,
            ProductService productService
    ) {
        this.transactionRepository = transactionRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public void createTransaction(PurchaseDto purchaseDto) {

        User currentUser = this.getCurrentUser();

        AddressDto shippingAddressDto = purchaseDto.getShippingAddressDto();
        AddressDto billingAddressDto = purchaseDto.getBillingAddressDto();
        List<OrderItemDto> orderItemDtos = purchaseDto.getOrderItems();

        Address shippingAddress = Address
                .builder()
                .city(shippingAddressDto.getCity())
                .province(shippingAddressDto.getProvince())
                .street(shippingAddressDto.getStreet())
                .zipCode(shippingAddressDto.getZipCode())
                .build();

        Address billingAddress = Address
                .builder()
                .city(billingAddressDto.getCity())
                .province(billingAddressDto.getProvince())
                .street(billingAddressDto.getStreet())
                .zipCode(billingAddressDto.getZipCode())
                .build();

        Address savedShippingAddress = this.addressRepository.save(shippingAddress);
        Address savedBillingAddress = this.addressRepository.save(billingAddress);

        List<OrderItem> orderItems = orderItemDtos.stream().map((orderItemDto -> {

            return OrderItem
                    .builder()
                    .quantity(orderItemDto.getQuantity())
                    .unitPrice(orderItemDto.getUnitPrice())
                    .product(this.productService.convertToEntity(orderItemDto.getProduct()))
                    .build();
        })).toList();

        BigDecimal totalAmountCost = BigDecimal.valueOf(0);

        for (OrderItem orderItem : orderItems) {
            totalAmountCost = totalAmountCost.add(BigDecimal.valueOf(orderItem.getQuantity()).multiply(orderItem.getUnitPrice()));
        }

        Transaction transaction = Transaction
                .builder()
                .date(new Date())
                .name("Buy products")
                .billing_address(savedBillingAddress)
                .shippingAddress(savedShippingAddress)
                .description("Buy devices for hydroponics farming")
                .amount(totalAmountCost)
                .status("Pending")
                .paymentMethod(purchaseDto.getPaymentMethod())
                .user(currentUser)
                .orderItems(orderItems)
                .build();

        orderItems.forEach(orderItem -> orderItem.setTransaction(transaction));

        this.transactionRepository.save(transaction);

    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
