package com.github.manimovassagh.immo_finder.rent_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentServiceApplication.class, args);
	}
}

@RestController
@RequestMapping("/api/v1/rent")
class RentController {
	@GetMapping
	public String getRent() {
		return "Hello World";
	}
}