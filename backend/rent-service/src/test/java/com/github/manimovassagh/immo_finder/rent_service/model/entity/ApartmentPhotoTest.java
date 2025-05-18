package com.github.manimovassagh.immo_finder.rent_service.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class ApartmentPhotoTest {

    @Test
    public void testApartmentPhotoGettersAndSetters() {
        ApartmentPhoto photo = new ApartmentPhoto();
        UUID id = UUID.randomUUID();
        ApartmentForRent apartment = new ApartmentForRent();
        String url = "http://example.com/photo.jpg";
        Integer position = 1;
        LocalDateTime uploadedAt = LocalDateTime.now();

        photo.setId(id);
        photo.setApartment(apartment);
        photo.setUrl(url);
        photo.setPosition(position);
        photo.setUploadedAt(uploadedAt);

        assertEquals(id, photo.getId());
        assertEquals(apartment, photo.getApartment());
        assertEquals(url, photo.getUrl());
        assertEquals(position, photo.getPosition());
        assertEquals(uploadedAt, photo.getUploadedAt());
    }
} 