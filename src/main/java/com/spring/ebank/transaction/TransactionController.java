package com.spring.ebank.transaction;

import ch.qos.logback.classic.Logger;
import com.spring.ebank.service.JsonKafkaProducer;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class TransactionController {
    private JsonKafkaProducer kafkaProducer;
    //public static final Logger logger = (Logger) LoggerFactory.getLogger(TransactionController.class);

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

        LocalDate currentYearMonth=LocalDate.now().withDayOfMonth(1);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        try {

            YearMonth inputYearMonth = YearMonth.parse(yearMonth, formatter);

            if (YearMonth.parse(yearMonth).atDay(1).isAfter(currentYearMonth)) {
                return new ResponseEntity<>("input date invalid", HttpStatus.FORBIDDEN);
            }
        }
        catch(DateTimeParseException e){
            return new ResponseEntity<>("invalid date format", HttpStatus.BAD_REQUEST);
        }

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
