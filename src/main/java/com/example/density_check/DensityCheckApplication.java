package com.example.density_check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DensityCheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(DensityCheckApplication.class, args);
	}

}
