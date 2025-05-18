package com.github.manimovassagh.immo_finder.rent_service.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.Address;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.ApartmentForRent;
import com.github.manimovassagh.immo_finder.rent_service.service.ApartmentService;

@WebMvcTest(ApartmentController.class)
public class ApartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApartmentService apartmentService;

    @Test
    public void testCreateApartment() throws Exception {
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

        // Mock service response
        when(apartmentService.createApartment(any(ApartmentForRent.class))).thenReturn(apartment);

        // Perform request and verify response
        mockMvc.perform(post("/api/v1/apartments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apartment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Apartment"))
                .andExpect(jsonPath("$.description").value("A test apartment"))
                .andExpect(jsonPath("$.pricePerMonth").value(1000.00))
                .andExpect(jsonPath("$.area").value(80.00))
                .andExpect(jsonPath("$.rooms").value(2))
                .andExpect(jsonPath("$.bathrooms").value(1))
                .andExpect(jsonPath("$.floor").value(1))
                .andExpect(jsonPath("$.isFurnished").value(true))
                .andExpect(jsonPath("$.isAvailable").value(true))
                .andExpect(jsonPath("$.address.street").value("Test Street"))
                .andExpect(jsonPath("$.address.houseNumber").value("123"))
                .andExpect(jsonPath("$.address.postalCode").value("12345"))
                .andExpect(jsonPath("$.address.city").value("Test City"))
                .andExpect(jsonPath("$.address.country").value("DE"));
    }
} 