package com.github.manimovassagh.immo_finder.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

import com.github.manimovassagh.immo_finder.models.Photo;
import com.github.manimovassagh.immo_finder.models.RentApartment;
import com.github.manimovassagh.immo_finder.repositories.RentApartmentRepository;

public class PhotoControllerEdgeCaseTest {

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
    void testUploadFileWithInvalidContentType() throws IOException {
        // Create a file with a non-image content type
        MockMultipartFile textFile = new MockMultipartFile(
                "files", 
                "document.txt", 
                "text/plain", 
                "This is a text file, not an image".getBytes());

        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                apartmentId,
                List.of(textFile));

        // Verify the response is OK (controller doesn't validate content type)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the repository was called to save the apartment
        verify(rentApartmentRepository, times(1)).save(apartment);

        // Verify that a photo was added to the apartment
        assertEquals(1, apartment.getPhotos().size());

        // Verify the file was saved with the correct name
        Photo savedPhoto = apartment.getPhotos().get(0);
        assertTrue(savedPhoto.getFileName().endsWith("document.txt"));
    }

    @Test
    void testUploadFileWithLongFilename() throws IOException {
        // Create a filename that is long but not too long for the file system
        StringBuilder longNameBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            longNameBuilder.append("very_long_name_");
        }
        longNameBuilder.append(".jpg");
        String longFilename = longNameBuilder.toString();

        // Create a file with the long filename
        MockMultipartFile fileWithLongName = new MockMultipartFile(
                "files", 
                longFilename, 
                "image/jpeg", 
                "test image content".getBytes());

        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                apartmentId,
                List.of(fileWithLongName));

        // Verify the response is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the repository was called to save the apartment
        verify(rentApartmentRepository, times(1)).save(apartment);

        // Verify that a photo was added to the apartment
        assertEquals(1, apartment.getPhotos().size());

        // Verify the file was saved with the correct name
        Photo savedPhoto = apartment.getPhotos().get(0);
        assertTrue(savedPhoto.getFileName().endsWith(longFilename));

        // Verify the file exists on disk
        Path savedFilePath = tempDir.resolve(savedPhoto.getFileName());
        assertTrue(Files.exists(savedFilePath));
    }

    @Test
    void testUploadFileWithSpecialCharactersInFilename() throws IOException {
        // Create a filename with special characters
        String filenameWithSpecialChars = "special@#$%^&*()_+{}[]|;:,.<>?.jpg";

        // Create a file with the special character filename
        MockMultipartFile fileWithSpecialChars = new MockMultipartFile(
                "files", 
                filenameWithSpecialChars, 
                "image/jpeg", 
                "test image content".getBytes());

        // Call the controller method
        ResponseEntity<?> response = photoController.uploadPhotos(
                apartmentId,
                List.of(fileWithSpecialChars));

        // Verify the response is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the repository was called to save the apartment
        verify(rentApartmentRepository, times(1)).save(apartment);

        // Verify that a photo was added to the apartment
        assertEquals(1, apartment.getPhotos().size());

        // Verify the file was saved with the correct name
        Photo savedPhoto = apartment.getPhotos().get(0);
        assertTrue(savedPhoto.getFileName().endsWith(filenameWithSpecialChars));

        // Verify the file exists on disk
        Path savedFilePath = tempDir.resolve(savedPhoto.getFileName());
        assertTrue(Files.exists(savedFilePath));
    }
}
