package com.github.manimovassagh.immo_finder.rent_service.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ApartmentDTO(
    UUID id,
    
    @NotBlank
    @Size(min = 1, max = 255)
    String title,
    
    @NotBlank
    @Size(min = 1, max = 1000)
    String description,
    
    @NotNull
    @Min(0)
    BigDecimal coldRent,
    
    @NotNull
    @Min(0)
    BigDecimal additionalCosts,
    
    @NotNull
    @Min(0)
    BigDecimal totalPrice,
    
    @NotNull
    @Min(1)
    Integer numberOfRooms,
    
    @NotNull
    @Min(1)
    Integer numberOfBathrooms,
    
    @NotNull
    @Min(1)
    BigDecimal area,
    
    @NotNull
    Boolean isAvailable,
    
    @NotNull
    @Valid
    AddressDTO address
) {
    public ApartmentDTO {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
        if (coldRent == null || coldRent.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cold rent cannot be negative");
        }
        if (additionalCosts == null || additionalCosts.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Additional costs cannot be negative");
        }
        if (totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        if (!totalPrice.equals(coldRent.add(additionalCosts))) {
            throw new IllegalArgumentException("Total price must be equal to cold rent plus additional costs");
        }
        if (numberOfRooms == null || numberOfRooms < 1) {
            throw new IllegalArgumentException("Number of rooms must be at least 1");
        }
        if (numberOfBathrooms == null || numberOfBathrooms < 1) {
            throw new IllegalArgumentException("Number of bathrooms must be at least 1");
        }
        if (area == null || area.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Area must be positive");
        }
        if (isAvailable == null) {
            throw new IllegalArgumentException("Availability status must be specified");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
    }
} 