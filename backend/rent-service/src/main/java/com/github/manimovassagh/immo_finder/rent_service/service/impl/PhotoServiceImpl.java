package com.github.manimovassagh.immo_finder.rent_service.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.manimovassagh.immo_finder.rent_service.config.FileStorageProperties;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.Apartment;
import com.github.manimovassagh.immo_finder.rent_service.repository.ApartmentRepository;
import com.github.manimovassagh.immo_finder.rent_service.service.PhotoService;
import com.github.manimovassagh.immo_finder.rent_service.util.SecurityUtils;

@Service
public class PhotoServiceImpl implements PhotoService {

    private static final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);

    private final Path fileStorageLocation;
    private final ApartmentRepository apartmentRepository;
    private final String baseUploadDirName;
    private final FileStorageProperties fileStorageProperties;

    public PhotoServiceImpl(FileStorageProperties fileStorageProperties, ApartmentRepository apartmentRepository) {
        this.fileStorageProperties = fileStorageProperties;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.baseUploadDirName = Paths.get(fileStorageProperties.getUploadDir()).normalize().toString();
        this.apartmentRepository = apartmentRepository;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    @Transactional
    public List<String> savePhotos(UUID apartmentId, List<MultipartFile> photos) throws IOException {
        String currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId == null) {
            throw new IllegalStateException("User ID could not be determined. Authentication is required.");
        }

        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new IllegalArgumentException("Apartment not found with ID: " + apartmentId));

        if (!apartment.getUserId().equals(currentUserId)) {
            throw new SecurityException("User not authorized to add photos to apartment ID: " + apartmentId);
        }

        Path userApartmentPath = this.fileStorageLocation.resolve(currentUserId).resolve(apartmentId.toString());
        Files.createDirectories(userApartmentPath);

        List<String> savedFileRelativePaths = new ArrayList<>();

        for (MultipartFile photo : photos) {
            if (photo.isEmpty()) {
                logger.warn("Skipping empty photo file for apartment ID: {}", apartmentId);
                continue;
            }

            String originalFileName = Objects.requireNonNull(photo.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + "_" + originalFileName.replaceAll("[^a-zA-Z0-9._-]", "");

            Path targetLocation = userApartmentPath.resolve(fileName);
            Files.copy(photo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            String relativePath = Paths.get(currentUserId, apartmentId.toString(), fileName).toString();
            apartment.addPhotoPath(relativePath);
            savedFileRelativePaths.add(relativePath);
            logger.info("Saved photo {} for apartment {} by user {}. Relative path: {}", fileName, apartmentId, currentUserId, relativePath);
        }
        
        if (!savedFileRelativePaths.isEmpty()) {
            apartmentRepository.save(apartment);
        }
        
        return savedFileRelativePaths;
    }
} 