package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDto> createTransaction(@RequestBody PurchaseDto purchaseDto) {

        this.transactionService.createTransaction(purchaseDto);

        return new ResponseEntity<>(new MessageResponseDto("Transaction created successfully."), HttpStatus.CREATED);
    }
}
