package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.shared.MessageResponseDto;

import java.util.List;

public interface TransactionService {
    void createTransaction(PurchaseDto purchaseDto);
    List<TransactionDto> getAllTransactions();
    TransactionDto getTransactionById(int transactionId);
    TransactionDto approveTransactionById(int transactionId);
}
