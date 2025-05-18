package com.github.manimovassagh.immo_finder.rent_service.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class ApartmentForRentTest {

    @Test
    public void testApartmentForRentGettersAndSetters() {
        ApartmentForRent apartment = new ApartmentForRent();
        UUID id = UUID.randomUUID();
        String title = "Test Apartment";
        String description = "Test Description";
        BigDecimal pricePerMonth = new BigDecimal("1000.00");
        BigDecimal area = new BigDecimal("50.00");
        Integer rooms = 2;
        Integer bathrooms = 1;
        Integer floor = 1;
        LocalDate availableFrom = LocalDate.now();
        Boolean isFurnished = true;
        Address address = new Address();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        apartment.setId(id);
        apartment.setTitle(title);
        apartment.setDescription(description);
        apartment.setPricePerMonth(pricePerMonth);
        apartment.setArea(area);
        apartment.setRooms(rooms);
        apartment.setBathrooms(bathrooms);
        apartment.setFloor(floor);
        apartment.setAvailableFrom(availableFrom);
        apartment.setIsFurnished(isFurnished);
        apartment.setAddress(address);
        apartment.setCreatedAt(createdAt);
        apartment.setUpdatedAt(updatedAt);

        assertEquals(id, apartment.getId());
        assertEquals(title, apartment.getTitle());
        assertEquals(description, apartment.getDescription());
        assertEquals(pricePerMonth, apartment.getPricePerMonth());
        assertEquals(area, apartment.getArea());
        assertEquals(rooms, apartment.getRooms());
        assertEquals(bathrooms, apartment.getBathrooms());
        assertEquals(floor, apartment.getFloor());
        assertEquals(availableFrom, apartment.getAvailableFrom());
        assertEquals(isFurnished, apartment.getIsFurnished());
        assertEquals(address, apartment.getAddress());
        assertEquals(createdAt, apartment.getCreatedAt());
        assertEquals(updatedAt, apartment.getUpdatedAt());
    }
} 