package com.github.manimovassagh.immo_finder.migrations;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.manimovassagh.immo_finder.models.Photo;
import com.github.manimovassagh.immo_finder.models.RentApartment;
import com.github.manimovassagh.immo_finder.repositories.RentApartmentRepository;

@SpringBootTest
@Testcontainers
public class MockDataMigrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private RentApartmentRepository rentApartmentRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Test
    @Transactional
    void testMockDataMigration() {
        // The UUID of the mock apartment we inserted in V2__insert_mock_data.sql
        UUID mockApartmentId = UUID.fromString("b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

        // Fetch the apartment from the database
        Optional<RentApartment> apartmentOpt = rentApartmentRepository.findById(mockApartmentId);

        // Assert that the apartment exists
        assertTrue(apartmentOpt.isPresent(), "Mock apartment should exist in the database");

        RentApartment apartment = apartmentOpt.get();

        // Assert that the apartment has the expected properties
        assertEquals("Beautiful Apartment in Berlin", apartment.getTitle());
        assertEquals(3, apartment.getRooms());

        // Assert that the apartment has an address
        assertNotNull(apartment.getAddress(), "Apartment should have an address");
        assertEquals("Sample Street", apartment.getAddress().getStreet());
        assertEquals("12345", apartment.getAddress().getPostalCode());

        // Assert that the apartment has photos
        List<Photo> photos = apartment.getPhotos();
        assertNotNull(photos, "Apartment should have photos");
        assertEquals(2, photos.size(), "Apartment should have 2 photos");

        // Check that the photos have the expected properties
        boolean foundImage1 = false;
        boolean foundImage2 = false;

        for (Photo photo : photos) {
            if (photo.getFileName().contains("image1.jpg")) {
                foundImage1 = true;
                assertEquals("/images/d1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11_image1.jpg", photo.getUrl());
            } else if (photo.getFileName().contains("image2.jpg")) {
                foundImage2 = true;
                assertEquals("/images/e1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11_image2.jpg", photo.getUrl());
            }
        }

        assertTrue(foundImage1, "Should find photo with image1.jpg");
        assertTrue(foundImage2, "Should find photo with image2.jpg");

        System.out.println("[DEBUG_LOG] Mock data migration test passed successfully");
    }
}
