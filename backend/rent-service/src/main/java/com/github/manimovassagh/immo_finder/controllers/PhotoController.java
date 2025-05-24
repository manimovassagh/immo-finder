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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        // Initialize photos list if null
        if (apartment.getPhotos() == null) {
            apartment.setPhotos(new ArrayList<>());
        }

        // Create directory if it doesn't exist
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // Check for duplicate photos
        List<String> existingFileNames = apartment.getPhotos().stream()
                .map(photo -> photo.getFileName().substring(photo.getFileName().indexOf('_') + 1))
                .toList();

        List<MultipartFile> newFiles = files.stream()
                .filter(file -> !existingFileNames.contains(StringUtils.cleanPath(file.getOriginalFilename())))
                .toList();

        // If all files are duplicates, return BAD_REQUEST
        if (newFiles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No new photos to upload or all photos already exist");
        }

        // Process only new files
        List<Photo> newPhotos = new ArrayList<>();
        int position = apartment.getPhotos().size(); // Start position after existing photos

        for (MultipartFile file : newFiles) {
            String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());
            String url = "/" + uploadDir + "/" + fileName;
            Photo photo = Photo.builder()
                    .fileName(fileName)
                    .url(url)
                    .position(position++)
                    .apartment(apartment)
                    .build();
            newPhotos.add(photo);
        }

        apartment.getPhotos().addAll(newPhotos);
        rentApartmentRepository.save(apartment);
        return ResponseEntity.ok(newPhotos);
    }
}
