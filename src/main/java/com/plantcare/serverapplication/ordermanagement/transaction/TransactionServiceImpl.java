package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.notificationmanagement.notification.Notification;
import com.plantcare.serverapplication.notificationmanagement.notification.NotificationRepository;
import com.plantcare.serverapplication.ordermanagement.address.Address;
import com.plantcare.serverapplication.ordermanagement.address.AddressDto;
import com.plantcare.serverapplication.ordermanagement.address.AddressRepository;
import com.plantcare.serverapplication.ordermanagement.orderitem.OrderItem;
import com.plantcare.serverapplication.ordermanagement.orderitem.OrderItemDto;
import com.plantcare.serverapplication.ordermanagement.orderitem.OrderItemService;
import com.plantcare.serverapplication.ordermanagement.product.ProductService;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.usermanagement.subscription.PurchaseSubscriptionDto;
import com.plantcare.serverapplication.usermanagement.subscription.Subscription;
import com.plantcare.serverapplication.usermanagement.subscription.SubscriptionRepository;
import com.plantcare.serverapplication.usermanagement.subscriptiontype.SubscriptionType;
import com.plantcare.serverapplication.usermanagement.subscriptiontype.SubscriptionTypeRepository;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import com.plantcare.serverapplication.usermanagement.user.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final NotificationRepository notificationRepository;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            SubscriptionRepository subscriptionRepository,
            SubscriptionTypeRepository subscriptionTypeRepository,
            AddressRepository addressRepository,
            UserRepository userRepository,
            ProductService productService,
            OrderItemService orderItemService,
            NotificationRepository notificationRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionTypeRepository = subscriptionTypeRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void createTransactionByProducts(PurchaseProductDto purchaseDto) {

        User currentUser = this.getCurrentUser();

        AddressDto shippingAddressDto = purchaseDto.getShippingAddressDto();
        AddressDto billingAddressDto = purchaseDto.getBillingAddressDto();
        List<OrderItemDto> orderItemDtos = purchaseDto.getOrderItems();

        // fix - include Address ID
        Address shippingAddress = Address
                .builder()
                .city(shippingAddressDto.getCity())
                .province(shippingAddressDto.getProvince())
                .street(shippingAddressDto.getStreet())
                .zipCode(shippingAddressDto.getZipCode())
                .build();

        // fix - include address ID
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

        Transaction newTransaction = Transaction
                .builder()
                .date(new Date())
                .name("Buy products")
                .billingAddress(savedBillingAddress)
                .shippingAddress(savedShippingAddress)
                .description("Buy devices for hydroponics farming")
                .amount(totalAmountCost)
                .status(TransactionStatus.PENDING)
                .paymentMethod(purchaseDto.getPaymentMethod())
                .user(currentUser)
                .orderItems(orderItems)
                .build();

        Notification notification = Notification
                .builder()
                .title("Products Transaction")
                .date(new Date())
                .content("You have ordered products for farming.")
                .isReadNotification(false)
                .build();


        Notification savedNotification = this.notificationRepository.save(notification);

        currentUser.getNotifications().add(savedNotification);

        orderItems.forEach(orderItem -> orderItem.setTransaction(newTransaction));

        this.transactionRepository.save(newTransaction);

    }

    @Override
    public void createTransactionBySubscription(PurchaseSubscriptionDto purchaseSubscriptionDto) {
        User currentUser = this.getCurrentUser();

        if (currentUser.getSubscription() != null) {
            throw new BadCredentialsException("User already has a subscription.");
        }

        SubscriptionType subscriptionType = this.subscriptionTypeRepository.findById(purchaseSubscriptionDto.getSubscriptionTypeId())
                .orElseThrow();

        AddressDto billingAddressDto = purchaseSubscriptionDto.getBillingAddressDto();

        // fix - include address ID
        Address billingAddress = Address
                .builder()
                .city(billingAddressDto.getCity())
                .province(billingAddressDto.getProvince())
                .street(billingAddressDto.getStreet())
                .zipCode(billingAddressDto.getZipCode())
                .build();

        Address savedBillingAddress = this.addressRepository.save(billingAddress);

        Transaction newTransaction = Transaction
                .builder()
                .date(new Date())
                .name(subscriptionType.getName())
                .billingAddress(savedBillingAddress)
                .description(subscriptionType.getSubscriptionBenefits())
                .amount(purchaseSubscriptionDto.getPrice())
                .status(TransactionStatus.APPROVED)
                .paymentMethod(purchaseSubscriptionDto.getPaymentMethod())
                .user(currentUser)
                .build();

        Notification notification = Notification
                .builder()
                .title("Subscription")
                .date(new Date())
                .content("You have officially subscribed to Plantcare premium.")
                .isReadNotification(false)
                .build();

        Notification savedNotification = this.notificationRepository.save(notification);

        currentUser.getNotifications().add(savedNotification);


        Subscription newSubscription = this.setUserSubscription(currentUser, subscriptionType);

        currentUser.setSubscription(newSubscription);

        this.subscriptionRepository.save(newSubscription);
        this.transactionRepository.save(newTransaction);
        this.notificationRepository.save(notification);
    }

    public Subscription setUserSubscription(User currentUser, SubscriptionType subscriptionType) {

        Date startDate = new Date();
        Date endDate;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        if (subscriptionType.getName().contains("Monthly")) {

            calendar.add(Calendar.MONTH, 1);
        } else if (subscriptionType.getName().contains("Yearly")) {

            calendar.add(Calendar.YEAR, 1);
        }

        endDate = calendar.getTime();

        Subscription subscription = Subscription
                .builder()
                .subscriptionType(subscriptionType)
                .startDate(startDate)
                .endDate(endDate)
                .user(currentUser)
                .build();

        return subscription;
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = this.transactionRepository.findAll();

        return transactions.stream().map(transaction -> convertToDtoForList(transaction)).toList();
    }

    @Override
    public TransactionDto getTransactionById(int transactionId) {
        Transaction transaction = this.transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));

        return this.convertToDto(transaction);
    }

    @Override
    public TransactionDto approveTransactionById(int transactionId) {
        Transaction transaction = this.transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));

        User currentUser = transaction.getUser();

        if (transaction.getStatus().name().equals("APPROVED")) {
            // throw exception
        }

        transaction.setStatus(TransactionStatus.APPROVED);

        User user = transaction.getUser();

        Notification notification = Notification
                .builder()
                .title("Approved Transaction")
                .date(new Date())
                .content("Admin has approved your transaction.")
                .isReadNotification(false)
                .build();


        Notification savedNotification = this.notificationRepository.save(notification);

        currentUser.getNotifications().add(savedNotification);

        Transaction approvedTransaction = this.transactionRepository.save(transaction);

        return this.convertToDto(approvedTransaction);
    }

    @Override
    public List<TransactionDto> getAllTransactionsByAdmin() {
        User currentUser = this.getCurrentUser();

        List<Transaction> transactions = currentUser.getTransactions();

        return transactions.stream()
                .map(transaction -> this.convertToDtoForList(transaction))
                .toList();
    }

    private TransactionDto convertToDto(Transaction transaction) {
        return TransactionDto
                .builder()
                .id(transaction.getId())
                .date(transaction.getDate())
                .name(transaction.getName())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .status(transaction.getStatus().name())
                .paymentMethod(transaction.getPaymentMethod())
                .orderItems(this.orderItemService.convertListToDto(transaction.getOrderItems()))
                .orderedBy(transaction.getUser().getEmail())
                .build();
    }

    // to be used by api for displaying list of transactions, no need for other in-depth transaction entity fields
    private TransactionDto convertToDtoForList(Transaction transaction) {
        return TransactionDto
                .builder()
                .id(transaction.getId())
                .date(transaction.getDate())
                .name(transaction.getName())
                .amount(transaction.getAmount())
                .status(transaction.getStatus().name())
                .orderedBy(transaction.getUser().getEmail())
                .build();
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
