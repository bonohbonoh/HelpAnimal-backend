package com.jeonggolee.helpanimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HelpAnimalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpAnimalBackendApplication.class, args);
	}

}
