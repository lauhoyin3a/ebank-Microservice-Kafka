package com.spring.ebank;

import com.spring.ebank.service.JsonKafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/messages")
public class MessageController {
    private JsonKafkaProducer kafkaProducer;

    public MessageController(JsonKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

  @PostMapping("/publish")
  public ResponseEntity<String> publish(@RequestBody Transaction transaction){
        kafkaProducer.sendMessage(transaction);
        return ResponseEntity.ok("Json Message sent");
  }


}
