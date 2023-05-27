package com.spring.ebank.transaction;

import com.spring.ebank.service.JsonKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class TransactionController {
    private JsonKafkaProducer kafkaProducer;

    @Autowired
    private TransactionService transactionService;
    public TransactionController(JsonKafkaProducer kafkaProducer, TransactionService transactionService) {
        this.kafkaProducer = kafkaProducer;
        this.transactionService= transactionService;
    }


    @GetMapping("/transactions/{customerId}/{yearMonth}")
    public ResponseEntity<String > getMonthTransactions(@PathVariable String customerId, @PathVariable String yearMonth){
        List<Transaction> transactions = transactionService.getMonthlyTransactions(customerId, yearMonth);
        double totalDebit=0;
        double totalCredit=0;
        for(Transaction transaction: transactions){
            kafkaProducer.sendMessage(transaction);
            if (transaction.isDebit()){
                totalDebit+=transaction.getAmount();

            }
            else{
                totalCredit+=transaction.getAmount();
            }

        }
        return new ResponseEntity<>("Total Debit: "+ totalDebit +", Total Credit:"+ totalCredit, HttpStatus.OK);
    }


}
