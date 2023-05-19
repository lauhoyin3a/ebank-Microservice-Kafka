package com.spring.ebank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate){
		return args -> {
		for(int i=0;i<100;i++){
			kafkaTemplate.send("ebank","test number"+i);
			}
		};
	}
@GetMapping("/123")
	public String apiRoot(){return "Hello!";}


}
