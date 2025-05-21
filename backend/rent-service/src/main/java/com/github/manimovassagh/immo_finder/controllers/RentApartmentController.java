package com.github.manimovassagh.immo_finder.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.manimovassagh.immo_finder.dtos.CreateApartmentRequest;
import com.github.manimovassagh.immo_finder.models.RentApartment;
import com.github.manimovassagh.immo_finder.repositories.RentApartmentRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/apartments")
@Validated
public class RentApartmentController {

    @Autowired
    private RentApartmentRepository rentApartmentRepository;

    @PostMapping
    public ResponseEntity<RentApartment> createApartment(@Valid @RequestBody CreateApartmentRequest request) {
        // Create a new RentApartment from the request
        RentApartment apartment = RentApartment.builder()
                .userId(request.getUserId() != null ? request.getUserId() : UUID.randomUUID())
                .title(request.getTitle())
                .description(request.getDescription())
                .basePrice(request.getBasePrice())
                .additionalCosts(request.getAdditionalCosts())
                .rooms(request.getRooms())
                .furnished(request.isFurnished())
                .hasParking(request.isHasParking())
                .hasBalcony(request.isHasBalcony())
                .hasStorage(request.isHasStorage())
                .size(request.getSize() != null ? request.getSize() : 0.0)
                .floor(request.getFloor())
                .totalFloors(request.getTotalFloors())
                .availableFrom(request.getAvailableFrom())
                .energyCertificate(request.getEnergyCertificate())
                .yearBuilt(request.getYearBuilt())
                .propertyType(request.getPropertyType())
                .petsAllowed(request.getPetsAllowed())
                .heatingType(request.getHeatingType())
                .elevator(request.getElevator())
                .barrierFree(request.getBarrierFree())
                .address(request.getAddress())
                .build();

        // Save the apartment
        RentApartment savedApartment = rentApartmentRepository.save(apartment);

        return new ResponseEntity<>(savedApartment, HttpStatus.CREATED);
    }
}
