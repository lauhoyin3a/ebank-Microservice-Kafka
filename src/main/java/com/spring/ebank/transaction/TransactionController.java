package com.spring.ebank.transaction;

import com.spring.ebank.service.JsonKafkaProducer;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/123")
    public String connectionTest(){
        return "connect to server";
    }

    @GetMapping("/transactions/{customerId}/{yearMonth}/{targetCurrency}")
    public ResponseEntity<String > getMonthTransactions(@PathVariable String customerId, @PathVariable String yearMonth, @PathVariable String targetCurrency){
        List<Transaction> transactions = transactionService.getMonthlyTransactions(customerId, yearMonth);
        double totalDebit=0;
        double totalCredit=0;
        String exchangeApiKey="56193325427c48a588d22f18";

        for(Transaction transaction: transactions){
            kafkaProducer.sendMessage(transaction);
            String amount= transaction.getAmount();
            String[] parts = amount.trim().split("\\s+", 2);
            String currency=parts[0];
            double amountValue;
            try {
                amountValue = Double.valueOf(parts[1]);
            } catch (ClassCastException e) {
                int intValue = Integer.parseInt(parts[1]);
                amountValue = intValue;
            }
            String apiUrl="https://v6.exchangerate-api.com/v6/"+exchangeApiKey+"/pair/"+currency+"/"+targetCurrency;
            RestTemplate restTemplate= new RestTemplate();
            String responseJson = restTemplate.getForObject(apiUrl, String.class);
            Document responseDocument = Document.parse(responseJson);
            double exchangeRate;
            Object rateValue = responseDocument.get("conversion_rate");

            if (rateValue instanceof Integer) {
                exchangeRate = (double) ((Integer) rateValue);
            } else {
                exchangeRate = (double) rateValue;
            }

            if (transaction.isDebit()){
                totalDebit+=amountValue*exchangeRate;

            }
            else{
                totalCredit+=amountValue*exchangeRate;
            }

        }
        return new ResponseEntity<>("Total Debit: "+ totalDebit + targetCurrency+", Total Credit: "+ totalCredit + targetCurrency, HttpStatus.OK);
    }


}
