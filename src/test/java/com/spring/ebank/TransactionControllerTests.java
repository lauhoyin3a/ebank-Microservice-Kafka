package com.spring.ebank;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.ebank.service.JsonKafkaProducer;
import com.spring.ebank.transaction.Transaction;
import com.spring.ebank.transaction.TransactionController;
import com.spring.ebank.transaction.TransactionService;

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
    public void testGetMonthTransactions() {

        ResponseEntity<String> response = transactionController.getMonthTransactions("123", "2023-04", "HKD");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Total Debit"));
        assertTrue(response.getBody().contains("Total Credit"));


    }
}