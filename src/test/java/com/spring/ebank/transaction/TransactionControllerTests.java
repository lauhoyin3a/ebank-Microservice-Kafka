package com.spring.ebank.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.ebank.service.JsonKafkaProducer;

@SpringBootTest
public class TransactionControllerTests {
    @Mock
    private JsonKafkaProducer kafkaProducer;
    @Mock
    private TransactionService transactionService;
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);  // Initialize mocks
        transactionController = new TransactionController(kafkaProducer, transactionService);
    }

    @Test
    public void testGetMonthTransactionsHKD() {

        ResponseEntity<String> response = transactionController.getMonthTransactions("123", "2023-04", "HKD");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Total Debit"));
        assertTrue(response.getBody().contains("Total Credit"));


    }

    @Test
    public void testGetMonthTransactionsUSD() {

        ResponseEntity<String> response = transactionController.getMonthTransactions("123", "2023-04", "USD");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Total Debit"));
        assertTrue(response.getBody().contains("Total Credit"));


    }
    @Test
    public void testGetMonthTransactionsNotExists() {

        ResponseEntity<String> response = transactionController.getMonthTransactions("1234", "2029-12", "USD");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Total Debit: 0.0USD, Total Credit: 0.0USD"));


    }
}