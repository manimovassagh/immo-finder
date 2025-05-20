package com.github.manimovassagh.immo_finder.rent_service.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDTO(
    UUID id,
    
    @NotBlank
    @Size(min = 1, max = 255)
    String street,
    
    @NotBlank
    @Size(min = 1, max = 10)
    String number,
    
    @NotBlank
    @Size(min = 5, max = 5)
    @Pattern(regexp = "\\d{5}")
    String postalCode,
    
    @NotBlank
    @Size(min = 1, max = 100)
    String city,
    
    @NotBlank
    @Size(min = 2, max = 2)
    String country
) {
    public AddressDTO {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be blank");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Number cannot be blank");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("Postal code cannot be blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be blank");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be blank");
        }
    }
} 