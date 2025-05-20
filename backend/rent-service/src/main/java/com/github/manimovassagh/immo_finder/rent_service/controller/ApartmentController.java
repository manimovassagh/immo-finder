package com.github.manimovassagh.immo_finder.rent_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.manimovassagh.immo_finder.rent_service.model.dto.ApartmentDTO;
import com.github.manimovassagh.immo_finder.rent_service.service.ApartmentService;
import com.github.manimovassagh.immo_finder.rent_service.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<ApartmentDTO>> createApartment(@Valid @RequestBody ApartmentDTO apartmentDTO) {
        try {
            ApartmentDTO createdApartment = apartmentService.createApartment(apartmentDTO);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Apartment created successfully", createdApartment));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("ERROR", "Failed to create apartment", null));
        }
    }
} 