package com.example.gongdon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GongdonApplication {

	public static void main(String[] args) {
		SpringApplication.run(GongdonApplication.class, args);
	}

}
