package com.spring.ebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions = transactionService.allTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/transactions/{customerId}")
    public ResponseEntity<Optional<Transaction>> getSingleTransaction(@PathVariable String customerId){
        Optional<Transaction> transaction = transactionService.singleTransaction(customerId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}