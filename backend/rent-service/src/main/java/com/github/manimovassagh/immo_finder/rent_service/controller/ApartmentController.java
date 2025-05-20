package com.github.manimovassagh.immo_finder.rent_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.manimovassagh.immo_finder.rent_service.model.dto.ApartmentDTO;
import com.github.manimovassagh.immo_finder.rent_service.service.ApartmentService;
import com.github.manimovassagh.immo_finder.rent_service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/apartments")
@Tag(name = "Apartment Management", description = "APIs for managing rental apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    @Operation(
        summary = "Create a new apartment",
        description = "Creates a new apartment with the provided details"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Apartment created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data or duplicate address"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    public ResponseEntity<ApiResponse<ApartmentDTO>> createApartment(
            @Parameter(description = "Apartment details", required = true)
            @Valid @RequestBody ApartmentDTO apartmentDTO) {
        try {
            ApartmentDTO createdApartment = apartmentService.createApartment(apartmentDTO);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Apartment created successfully", createdApartment));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("ERROR", "Failed to create apartment: " + e.getMessage(), null));
        }
    }
} 