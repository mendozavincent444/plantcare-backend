package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.usermanagement.subscription.PurchaseSubscriptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDto> createTransactionByProducts(@RequestBody PurchaseProductDto purchaseDto) {

        this.transactionService.createTransactionByProducts(purchaseDto);

        return new ResponseEntity<>(new MessageResponseDto("Transaction created successfully."), HttpStatus.CREATED);
    }

    @PostMapping("/subscription")
    public ResponseEntity<MessageResponseDto> createTransactionBySubscription(@RequestBody PurchaseSubscriptionDto purchaseSubscriptionDto) {

        this.transactionService.createTransactionBySubscription(purchaseSubscriptionDto);

        return new ResponseEntity<>(new MessageResponseDto("Transaction created successfully."), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = this.transactionService.getAllTransactions();

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactionsByAdmin() {
        List<TransactionDto> transactions = this.transactionService.getAllTransactionsByAdmin();

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable int transactionId) {

        TransactionDto transaction = this.transactionService.getTransactionById(transactionId);

        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/{transactionId}/approve")
    public ResponseEntity<TransactionDto> approveTransactionById(@PathVariable int transactionId) {

        TransactionDto transaction = this.transactionService.approveTransactionById(transactionId);

        return ResponseEntity.ok(transaction);
    }


}
