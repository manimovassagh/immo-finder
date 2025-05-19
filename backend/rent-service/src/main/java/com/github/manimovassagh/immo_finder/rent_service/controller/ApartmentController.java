package com.github.manimovassagh.immo_finder.rent_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.manimovassagh.immo_finder.rent_service.model.dto.ApartmentForRentDTO;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.ApartmentForRent;
import com.github.manimovassagh.immo_finder.rent_service.service.ApartmentService;
import com.github.manimovassagh.immo_finder.rent_service.util.ApartmentMapper;
import com.github.manimovassagh.immo_finder.rent_service.util.ApiResponse;
import com.github.manimovassagh.immo_finder.rent_service.util.GlobalExceptionHandler;

@RestController
@RequestMapping("/api/v1/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final ApartmentMapper apartmentMapper;

    public ApartmentController(ApartmentService apartmentService, ApartmentMapper apartmentMapper) {
        this.apartmentService = apartmentService;
        this.apartmentMapper = apartmentMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApartmentForRentDTO>> createApartment(@RequestBody ApartmentForRentDTO apartmentDTO) {
        try {
            ApartmentForRent apartment = apartmentMapper.toEntity(apartmentDTO);
            ApartmentForRent createdApartment = apartmentService.createApartment(apartment);
            ApartmentForRentDTO createdApartmentDTO = apartmentMapper.toDTO(createdApartment);
            
            ApiResponse<ApartmentForRentDTO> response = new ApiResponse<>("SUCCESS", "Apartment created successfully", createdApartmentDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return GlobalExceptionHandler.errorResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 