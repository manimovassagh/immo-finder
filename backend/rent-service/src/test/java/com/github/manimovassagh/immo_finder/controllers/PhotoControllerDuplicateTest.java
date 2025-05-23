package com.github.manimovassagh.immo_finder.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.manimovassagh.immo_finder.models.Photo;
import com.github.manimovassagh.immo_finder.models.RentApartment;
import com.github.manimovassagh.immo_finder.repositories.RentApartmentRepository;

public class PhotoControllerDuplicateTest {

    @Mock
    private RentApartmentRepository rentApartmentRepository;

    @InjectMocks
    private PhotoController photoController;

    @TempDir
    Path tempDir;

    private UUID apartmentId;
    private RentApartment apartment;
    private List<Photo> existingPhotos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Set up temp directory for uploads
        ReflectionTestUtils.setField(photoController, "uploadDir", tempDir.toString());
        
        // Create test apartment
        apartmentId = UUID.randomUUID();
        apartment = new RentApartment();
        apartment.setId(apartmentId);
        
        // Create existing photos
        existingPhotos = new ArrayList<>();
        Photo existingPhoto = Photo.builder()
                .fileName(UUID.randomUUID() + "_existing.jpg")
                .url("/images/existing.jpg")
                .position(Integer.valueOf(0))
                .apartment(apartment)
                .build();
        existingPhotos.add(existingPhoto);
        
        apartment.setPhotos(existingPhotos);
        
        // Mock repository
        when(rentApartmentRepository.findById(apartmentId)).thenReturn(Optional.of(apartment));
        when(rentApartmentRepository.save(any(RentApartment.class))).thenReturn(apartment);
    }

    @Test
    void testUploadNewPhoto() throws IOException {
        // Create a new photo file
        MockMultipartFile newFile = new MockMultipartFile(
                "files", 
                "new.jpg", 
                "image/jpeg", 
                "test image content".getBytes());
        
        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                apartmentId,
                List.of(newFile));
        
        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Verify that the repository was called to save the apartment
        verify(rentApartmentRepository, times(1)).save(apartment);
        
        // Verify that a new photo was added to the apartment
        assertEquals(2, apartment.getPhotos().size());
    }

    @Test
    void testUploadDuplicatePhoto() throws IOException {
        // Create a duplicate photo file (same name as existing)
        MockMultipartFile duplicateFile = new MockMultipartFile(
                "files", 
                "existing.jpg", 
                "image/jpeg", 
                "test image content".getBytes());
        
        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                apartmentId,
                List.of(duplicateFile));
        
        // Verify the response - should be BAD_REQUEST since all photos are duplicates
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("No new photos to upload or all photos already exist"));
        
        // Verify that the repository was not called to save the apartment
        verify(rentApartmentRepository, never()).save(apartment);
        
        // Verify that no new photos were added to the apartment
        assertEquals(1, apartment.getPhotos().size());
    }

    @Test
    void testUploadMixedPhotos() throws IOException {
        // Create one new and one duplicate photo
        MockMultipartFile newFile = new MockMultipartFile(
                "files", 
                "new.jpg", 
                "image/jpeg", 
                "test image content".getBytes());
        
        MockMultipartFile duplicateFile = new MockMultipartFile(
                "files", 
                "existing.jpg", 
                "image/jpeg", 
                "test image content".getBytes());
        
        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                apartmentId, 
                Arrays.asList(newFile, duplicateFile));
        
        // Verify the response - should be OK since at least one photo is new
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Verify that the repository was called to save the apartment
        verify(rentApartmentRepository, times(1)).save(apartment);
        
        // Verify that only one new photo was added to the apartment
        assertEquals(2, apartment.getPhotos().size());
    }
}