package com.plantcare.serverapplication.ordermanagement.transaction;

import com.plantcare.serverapplication.shared.MessageResponseDto;
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
    public ResponseEntity<MessageResponseDto> createTransaction(@RequestBody PurchaseDto purchaseDto) {

        this.transactionService.createTransaction(purchaseDto);

        return new ResponseEntity<>(new MessageResponseDto("Transaction created successfully."), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = this.transactionService.getAllTransactions();

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
