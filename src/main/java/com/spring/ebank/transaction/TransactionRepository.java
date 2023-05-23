package com.spring.ebank.transaction;

import com.spring.ebank.transaction.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends MongoRepository<Transaction, ObjectId> {
    Optional<Transaction> findTransactionByCustomerId(String imString);

    List<Transaction> findByCustomerIdAndValueDateBetween(String customerId, LocalDate startDate, LocalDate endDate);
}
