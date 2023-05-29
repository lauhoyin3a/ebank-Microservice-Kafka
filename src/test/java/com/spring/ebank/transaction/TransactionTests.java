package com.spring.ebank.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class TransactionTests {
    @Test
    public void testTransactionConstructor() {
        Transaction transaction = new Transaction("1234", "456", true, "CH33-0000-0000-0000-0000-0",
                LocalDateTime.parse("2023-05-27T12:34:56.000"), "test", "USD 10.00");

        assertEquals("1234", transaction.getCustomerId());
        assertEquals("456", transaction.getTransactionId());
        assertTrue(transaction.isDebit());
        assertEquals("CH33-0000-0000-0000-0000-0", transaction.getAccountIban());
        assertEquals(LocalDateTime.parse("2023-05-27T12:34:56.000"), transaction.getValueDate());
        assertEquals("test", transaction.getDescription());
        assertEquals("USD 10.00", transaction.getAmount());
    }

}
