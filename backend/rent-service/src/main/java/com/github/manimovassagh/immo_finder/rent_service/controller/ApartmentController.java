package com.github.manimovassagh.immo_finder.rent_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.manimovassagh.immo_finder.rent_service.model.entity.ApartmentForRent;
import com.github.manimovassagh.immo_finder.rent_service.service.ApartmentService;

@RestController
@RequestMapping("/api/v1/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public ResponseEntity<ApartmentForRent> createApartment(@RequestBody ApartmentForRent apartment) {
        ApartmentForRent createdApartment = apartmentService.createApartment(apartment);
        return new ResponseEntity<>(createdApartment, HttpStatus.CREATED);
    }
} 