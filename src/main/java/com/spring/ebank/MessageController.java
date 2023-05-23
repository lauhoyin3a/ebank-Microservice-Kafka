package com.spring.ebank;

import com.spring.ebank.service.JsonKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ap")
public class MessageController {
    private JsonKafkaProducer kafkaProducer;
    @Autowired
    private TransactionService transactionService;
    public MessageController(JsonKafkaProducer kafkaProducer, TransactionService transactionService) {
        this.kafkaProducer = kafkaProducer;
        this.transactionService= transactionService;
    }


    @GetMapping("/transactions/{customerId}/{yearMonth}")
    public ResponseEntity<List<Transaction>> getMonthTransactions(@PathVariable String customerId, @PathVariable String yearMonth){
        List<Transaction> transactions = transactionService.getMonthlyTransactions(customerId, yearMonth);
        for(Transaction transaction: transactions){
            kafkaProducer.sendMessage(transaction);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }


}
