package com.github.manimovassagh.immo_finder.rent_service.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.github.manimovassagh.immo_finder.rent_service.model.entity.Address;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.ApartmentForRent;

@SpringBootTest
@Transactional
public class ApartmentServiceIntegrationTest {

    @Autowired
    private ApartmentService apartmentService;

    @Test
    public void testCreateApartment() {
        // Create test data
        ApartmentForRent apartment = new ApartmentForRent();
        apartment.setTitle("Test Apartment");
        apartment.setDescription("A test apartment");
        apartment.setPricePerMonth(new BigDecimal("1000.00"));
        apartment.setArea(new BigDecimal("80.00"));
        apartment.setRooms(2);
        apartment.setBathrooms(1);
        apartment.setFloor(1);
        apartment.setAvailableFrom(LocalDate.now());
        apartment.setIsFurnished(true);

        // Create and set address
        Address address = new Address();
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setPostalCode("12345");
        address.setCity("Test City");
        address.setCountry("DE");
        apartment.setAddress(address);

        // Save apartment
        ApartmentForRent savedApartment = apartmentService.createApartment(apartment);

        // Verify
        assertNotNull(savedApartment);
        assertNotNull(savedApartment.getId());
        assertEquals("Test Apartment", savedApartment.getTitle());
        assertEquals(new BigDecimal("1000.00"), savedApartment.getPricePerMonth());
        assertEquals(new BigDecimal("80.00"), savedApartment.getArea());
        assertEquals(2, savedApartment.getRooms());
        assertEquals(1, savedApartment.getBathrooms());
        assertEquals(1, savedApartment.getFloor());
        assertEquals(LocalDate.now(), savedApartment.getAvailableFrom());
        assertEquals(true, savedApartment.getIsFurnished());
        assertEquals(true, savedApartment.getIsAvailable());
        assertNotNull(savedApartment.getAddress());
        assertEquals("Test Street", savedApartment.getAddress().getStreet());
        assertEquals("123", savedApartment.getAddress().getHouseNumber());
        assertEquals("12345", savedApartment.getAddress().getPostalCode());
        assertEquals("Test City", savedApartment.getAddress().getCity());
        assertEquals("DE", savedApartment.getAddress().getCountry());
    }
} 