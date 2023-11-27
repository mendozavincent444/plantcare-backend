package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.usermanagement.subscription.PurchaseSubscriptionDto;
import com.plantcare.serverapplication.usermanagement.subscription.Subscription;
import com.plantcare.serverapplication.usermanagement.subscriptiontype.SubscriptionType;
import com.plantcare.serverapplication.usermanagement.user.User;

import java.util.List;

public interface TransactionService {
    void createTransactionByProducts(PurchaseProductDto purchaseDto);
    void createTransactionBySubscription(PurchaseSubscriptionDto purchaseSubscriptionDto);
    List<TransactionDto> getAllTransactions();
    TransactionDto getTransactionById(int transactionId);
    TransactionDto approveTransactionById(int transactionId);
    List<TransactionDto> getAllTransactionsByAdmin();
    Subscription setUserSubscription(User currentUser, SubscriptionType subscriptionType);
}
