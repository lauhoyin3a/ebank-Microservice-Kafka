package com.spring.ebank.transaction;

import com.spring.ebank.transaction.Transaction;
import com.spring.ebank.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    public TransactionRepository transactionRepository;

    public List<Transaction> allTransactions(){

        return transactionRepository.findAll();
    }
    public Optional<Transaction> singleTransaction(String customerId){
        return transactionRepository.findTransactionByCustomerId(customerId);
    }
    public List<Transaction> getMonthlyTransactions(String customerId, String yearmonth) {
        // Parse the date string into a Java LocalDate object
        LocalDate firstDayOfMonth = LocalDate.parse(yearmonth +"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        LocalDate startDate = firstDayOfMonth;
        LocalDate endDate = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        // Retrieve the transactions for the given customer ID within the specified date range
        List<Transaction> transactions = transactionRepository.findByCustomerIdAndValueDateBetween(customerId, startDate, endDate);

        return transactions;
    }
}
