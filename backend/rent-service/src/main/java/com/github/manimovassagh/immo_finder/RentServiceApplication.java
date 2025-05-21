package com.github.manimovassagh.immo_finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.github.manimovassagh.immo_finder.rent_service.model.entity")
@EnableJpaRepositories("com.github.manimovassagh.immo_finder.rent_service.repository")
@ComponentScan(basePackages = "com.github.manimovassagh.immo_finder.rent_service")
public class RentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentServiceApplication.class, args);
	}
}

