package com.github.manimovassagh.immo_finder.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

public class PhotoControllerAdditionalTest {

    @Mock
    private RentApartmentRepository rentApartmentRepository;

    @InjectMocks
    private PhotoController photoController;

    @TempDir
    Path tempDir;

    private UUID apartmentId;
    private RentApartment apartment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Set up temp directory for uploads
        ReflectionTestUtils.setField(photoController, "uploadDir", tempDir.toString());
        
        // Create test apartment
        apartmentId = UUID.randomUUID();
        apartment = new RentApartment();
        apartment.setId(apartmentId);
        apartment.setPhotos(new ArrayList<>());
        
        // Mock repository
        when(rentApartmentRepository.findById(apartmentId)).thenReturn(Optional.of(apartment));
        when(rentApartmentRepository.save(any(RentApartment.class))).thenReturn(apartment);
    }

    @Test
    void testUploadPhotosForNonExistentApartment() throws IOException {
        // Create a UUID for a non-existent apartment
        UUID nonExistentId = UUID.randomUUID();
        
        // Mock repository to return empty for this ID
        when(rentApartmentRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        
        // Create a test photo file
        MockMultipartFile file = new MockMultipartFile(
                "files", 
                "test.jpg", 
                "image/jpeg", 
                "test image content".getBytes());
        
        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                nonExistentId,
                List.of(file));
        
        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Apartment not found", response.getBody());
        
        // Verify that the repository was not called to save anything
        verify(rentApartmentRepository, never()).save(any(RentApartment.class));
    }

    @Test
    void testUploadEmptyFile() throws IOException {
        // Create an empty photo file
        MockMultipartFile emptyFile = new MockMultipartFile(
                "files", 
                "empty.jpg", 
                "image/jpeg", 
                new byte[0]);
        
        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                apartmentId,
                List.of(emptyFile));
        
        // Verify the response is OK (empty files are still valid files)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Verify that the repository was called to save the apartment
        verify(rentApartmentRepository, times(1)).save(apartment);
        
        // Verify that a photo was added to the apartment
        assertEquals(1, apartment.getPhotos().size());
        
        // Verify the photo has zero bytes
        Path savedFilePath = tempDir.resolve(apartment.getPhotos().get(0).getFileName());
        assertEquals(0, Files.size(savedFilePath));
    }

    @Test
    void testPhotoPositionAssignment() throws IOException {
        // Create multiple photo files
        MockMultipartFile file1 = new MockMultipartFile(
                "files", 
                "photo1.jpg", 
                "image/jpeg", 
                "test image 1 content".getBytes());
        
        MockMultipartFile file2 = new MockMultipartFile(
                "files", 
                "photo2.jpg", 
                "image/jpeg", 
                "test image 2 content".getBytes());
        
        MockMultipartFile file3 = new MockMultipartFile(
                "files", 
                "photo3.jpg", 
                "image/jpeg", 
                "test image 3 content".getBytes());
        
        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                apartmentId,
                Arrays.asList(file1, file2, file3));
        
        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Verify that the repository was called to save the apartment
        verify(rentApartmentRepository, times(1)).save(apartment);
        
        // Verify that all photos were added to the apartment
        assertEquals(3, apartment.getPhotos().size());
        
        // Verify the positions are assigned correctly (0, 1, 2)
        assertEquals(Integer.valueOf(0), apartment.getPhotos().get(0).getPosition());
        assertEquals(Integer.valueOf(1), apartment.getPhotos().get(1).getPosition());
        assertEquals(Integer.valueOf(2), apartment.getPhotos().get(2).getPosition());
    }

    @Test
    void testPhotoUrlGeneration() throws IOException {
        // Create a photo file
        MockMultipartFile file = new MockMultipartFile(
                "files", 
                "test.jpg", 
                "image/jpeg", 
                "test image content".getBytes());
        
        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                apartmentId,
                List.of(file));
        
        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Verify that a photo was added to the apartment
        assertEquals(1, apartment.getPhotos().size());
        
        // Get the saved photo
        Photo savedPhoto = apartment.getPhotos().get(0);
        
        // Verify the URL format is correct
        String expectedUrlPrefix = "/" + tempDir.toString() + "/";
        assertTrue(savedPhoto.getUrl().startsWith(expectedUrlPrefix));
        assertTrue(savedPhoto.getUrl().endsWith("test.jpg"));
        
        // Verify the filename format is correct (UUID_originalname)
        String fileName = savedPhoto.getFileName();
        assertTrue(fileName.contains("_"));
        assertEquals("test.jpg", fileName.substring(fileName.indexOf('_') + 1));
    }
}