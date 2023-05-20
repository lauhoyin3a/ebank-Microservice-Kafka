package com.spring.ebank.service;

import com.spring.ebank.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    private KafkaTemplate<String, Transaction> kafkaTemplate;


    public JsonKafkaProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Transaction data){

        LOGGER.info(String.format("Message sent -> %s", data.toString()));

        Message<Transaction> message= MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "ebank")
                .build();

        kafkaTemplate.send(message);
    }

}
