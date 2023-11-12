package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.usermanagement.subscription.PurchaseSubscriptionDto;

import java.util.List;

public interface TransactionService {
    void createTransactionByProducts(PurchaseProductDto purchaseDto);
    void createTransactionBySubscription(PurchaseSubscriptionDto purchaseSubscriptionDto);
    List<TransactionDto> getAllTransactions();
    TransactionDto getTransactionById(int transactionId);
    TransactionDto approveTransactionById(int transactionId);
    List<TransactionDto> getAllTransactionsByAdmin();
}
