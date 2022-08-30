package com.hanghae.greenstep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GreenstepApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenstepApplication.class, args);
	}

}
