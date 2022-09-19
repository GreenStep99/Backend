package com.hanghae.greenstep;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
public class GreenstepApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenstepApplication.class, args);
	}

}
