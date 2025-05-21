package com.github.manimovassagh.immo_finder.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.github.manimovassagh.immo_finder.models.Address;
import com.github.manimovassagh.immo_finder.models.HeatingType;
import com.github.manimovassagh.immo_finder.models.PropertyType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateApartmentRequest {
    
    private UUID userId;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotNull(message = "Base price is required")
    @PositiveOrZero(message = "Base price must be positive or zero")
    private BigDecimal basePrice;
    
    @NotNull(message = "Additional costs are required")
    @PositiveOrZero(message = "Additional costs must be positive or zero")
    private BigDecimal additionalCosts;
    
    @NotNull(message = "Number of rooms is required")
    @Positive(message = "Number of rooms must be positive")
    private Integer rooms;
    
    private boolean furnished;
    private boolean hasParking;
    private boolean hasBalcony;
    private boolean hasStorage;
    
    @PositiveOrZero(message = "Size must be positive or zero")
    private Double size;
    
    private Integer floor;
    private Integer totalFloors;
    private LocalDate availableFrom;
    private Boolean energyCertificate;
    private Integer yearBuilt;
    private PropertyType propertyType;
    private Boolean petsAllowed;
    private HeatingType heatingType;
    private Boolean elevator;
    private Boolean barrierFree;
    
    @Valid
    private Address address;
}