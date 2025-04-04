package com.example.vmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("com.example.vmo.model")
@EnableJpaRepositories("com.example.vmo.repository")
@EnableTransactionManagement
public class VmoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmoApplication.class, args);
	}

}
