package com.github.manimovassagh.immo_finder.rent_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rent")
public class RentController {
    
    @GetMapping
    public String getRent() {
        return "Hello World from Rent Service";
    }
} 