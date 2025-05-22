package com.github.manimovassagh.immo_finder.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.manimovassagh.immo_finder.models.Photo;
import com.github.manimovassagh.immo_finder.models.RentApartment;
import com.github.manimovassagh.immo_finder.repositories.RentApartmentRepository;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final RentApartmentRepository rentApartmentRepository;

    @Value("${photo.upload.dir:images}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPhotos(@RequestParam("apartmentId") UUID apartmentId,
                                          @RequestParam("files") List<MultipartFile> files) throws IOException {
        Optional<RentApartment> apartmentOpt = rentApartmentRepository.findById(apartmentId);
        if (apartmentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");
        }
        RentApartment apartment = apartmentOpt.get();
        List<Photo> photos = new ArrayList<>();
        File dir = new File(uploadDir);
        if (!dir.exists());
            //dir.mkdirs();
        int position = apartment.getPhotos() != null ? apartment.getPhotos().size() : 0;

        // Create a set of existing original filenames for this apartment
        Set<String> existingFilenames = new HashSet<>();
        if (apartment.getPhotos() != null) {
            for (Photo photo : apartment.getPhotos()) {
                // Extract original filename from the stored filename (after the UUID and underscore)
                String storedFilename = photo.getFileName();
                int underscoreIndex = storedFilename.indexOf('_');
                if (underscoreIndex >= 0 && underscoreIndex < storedFilename.length() - 1) {
                    String originalFilename = storedFilename.substring(underscoreIndex + 1);
                    existingFilenames.add(originalFilename);
                }
            }
        }

        for (MultipartFile file : files) {
            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            // Skip this file if it already exists for this apartment
            if (existingFilenames.contains(originalFilename)) {
                continue;
            }

            String fileName = UUID.randomUUID() + "_" + originalFilename;
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());
            String url = "/" + uploadDir + "/" + fileName;
            Photo photo = Photo.builder()
                    .fileName(fileName)
                    .url(url)
                    .position(position++)
                    .apartment(apartment)
                    .build();
            photos.add(photo);

            // Add to set of existing filenames to prevent duplicates within the same upload batch
            existingFilenames.add(originalFilename);
        }

        if (photos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No new photos to upload or all photos already exist");
        }

        if (apartment.getPhotos() == null) {
            apartment.setPhotos(new ArrayList<>());
        }
        apartment.getPhotos().addAll(photos);
        RentApartment savedApartment = rentApartmentRepository.save(apartment);
        return ResponseEntity.ok(savedApartment.getPhotos());
    }
}
