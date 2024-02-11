package com.example.agregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AgregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgregatorApplication.class, args);
	}

}
