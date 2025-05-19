package com.github.manimovassagh.immo_finder.rent_service.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record ApartmentForRentDTO(
    UUID id,
    UUID userId,
    String title,
    String description,
    BigDecimal pricePerMonth,
    BigDecimal area,
    Integer rooms,
    Boolean isAvailable,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public ApartmentForRentDTO {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(pricePerMonth, "Price per month cannot be null");
        Objects.requireNonNull(area, "Area cannot be null");
        Objects.requireNonNull(rooms, "Rooms cannot be null");
        
        if (title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        if (pricePerMonth.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price per month must be greater than 0");
        }
        if (area.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Area must be greater than 0");
        }
        if (rooms <= 0) {
            throw new IllegalArgumentException("Number of rooms must be greater than 0");
        }
    }
} 