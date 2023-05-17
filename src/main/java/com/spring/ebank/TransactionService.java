package com.spring.ebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
