package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.shared.MessageResponseDto;

public interface TransactionService {

    void createTransaction(PurchaseDto purchaseDto);
}
