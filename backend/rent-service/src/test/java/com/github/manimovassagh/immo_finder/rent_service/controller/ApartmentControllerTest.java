package com.github.manimovassagh.immo_finder.rent_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.manimovassagh.immo_finder.rent_service.model.Apartment;
import com.github.manimovassagh.immo_finder.rent_service.service.ApartmentService;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ApartmentControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("rent_service_test_db")
            .withUsername("test_user")
            .withPassword("test_password");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApartmentService apartmentService;

    @Test
    void createApartment_ShouldReturnCreatedApartment() throws Exception {
        // Arrange
        Apartment apartment = new Apartment();
        apartment.setTitle("Modern Apartment");
        apartment.setDescription("Beautiful apartment in city center");
        apartment.setAddress("123 Main St");
        apartment.setCity("Berlin");
        apartment.setPostalCode("10115");
        apartment.setPrice(new BigDecimal("1200.00"));
        apartment.setNumberOfRooms(2);
        apartment.setSurfaceArea(75.5);

        // Act & Assert
        mockMvc.perform(post("/api/v1/apartments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apartment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Modern Apartment"))
                .andExpect(jsonPath("$.description").value("Beautiful apartment in city center"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.city").value("Berlin"))
                .andExpect(jsonPath("$.postalCode").value("10115"))
                .andExpect(jsonPath("$.price").value(1200.00))
                .andExpect(jsonPath("$.numberOfRooms").value(2))
                .andExpect(jsonPath("$.surfaceArea").value(75.5))
                .andExpect(jsonPath("$.isAvailable").value(true))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }
} 