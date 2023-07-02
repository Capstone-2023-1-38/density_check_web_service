package com.example.density_check_web_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DensityCheckWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DensityCheckWebServiceApplication.class, args);
	}

}
