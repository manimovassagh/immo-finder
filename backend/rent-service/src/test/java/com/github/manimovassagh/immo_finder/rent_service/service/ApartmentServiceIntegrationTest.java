package com.github.manimovassagh.immo_finder.rent_service.service;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.manimovassagh.immo_finder.rent_service.model.Apartment;
import com.github.manimovassagh.immo_finder.rent_service.repository.ApartmentRepository;

@SpringBootTest
@Testcontainers
@Transactional
class ApartmentServiceIntegrationTest {

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
    private ApartmentService apartmentService;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @BeforeEach
    void setUp() {
        apartmentRepository.deleteAll();
    }

    @Test
    void createApartment_ShouldSaveAndReturnApartment() {
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

        // Act
        Apartment savedApartment = apartmentService.createApartment(apartment);

        // Assert
        assertThat(savedApartment).isNotNull();
        assertThat(savedApartment.getId()).isNotNull();
        assertThat(savedApartment.getTitle()).isEqualTo("Modern Apartment");
        assertThat(savedApartment.getIsAvailable()).isTrue();
        assertThat(savedApartment.getCreatedAt()).isNotNull();
        assertThat(savedApartment.getUpdatedAt()).isNotNull();

        // Verify it was actually saved in the database
        Apartment foundApartment = apartmentRepository.findById(savedApartment.getId()).orElse(null);
        assertThat(foundApartment).isNotNull();
        assertThat(foundApartment.getTitle()).isEqualTo("Modern Apartment");
    }
} 