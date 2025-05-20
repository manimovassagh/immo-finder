package com.github.manimovassagh.immo_finder.rent_service.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    List<String> savePhotos(UUID apartmentId, List<MultipartFile> photos) throws IOException;
} 