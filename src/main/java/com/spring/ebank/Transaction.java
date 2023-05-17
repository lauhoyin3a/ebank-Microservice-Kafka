package com.spring.ebank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TimeSeries;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Document(collection = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Transaction {
    @Id
    private ObjectId id;

    private String customerId;

    private String transactionId;
    private String currency;

    private boolean debit;

    private String accountIban;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime valueDate;
    private String description;
}
