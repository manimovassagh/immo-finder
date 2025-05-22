package com.github.manimovassagh.immo_finder.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class PhotoTest {
    @Test
    void testPhotoBuilderAndGetters() {
        UUID id = UUID.randomUUID();
        String fileName = "test.jpg";
        String url = "http://localhost/images/test.jpg";
        int position = 1;
        RentApartment apartment = RentApartment.builder().build();

        Photo photo = Photo.builder()
                .id(id)
                .fileName(fileName)
                .url(url)
                .position(position)
                .apartment(apartment)
                .build();

        assertEquals(id, photo.getId());
        assertEquals(fileName, photo.getFileName());
        assertEquals(url, photo.getUrl());
        assertEquals(position, photo.getPosition());
        assertEquals(apartment, photo.getApartment());
    }

    @Test
    void testPhotoSetters() {
        Photo photo = new Photo();
        UUID id = UUID.randomUUID();
        String fileName = "test2.jpg";
        String url = "http://localhost/images/test2.jpg";
        int position = 2;
        RentApartment apartment = RentApartment.builder().build();

        photo.setId(id);
        photo.setFileName(fileName);
        photo.setUrl(url);
        photo.setPosition(position);
        photo.setApartment(apartment);

        assertEquals(id, photo.getId());
        assertEquals(fileName, photo.getFileName());
        assertEquals(url, photo.getUrl());
        assertEquals(position, photo.getPosition());
        assertEquals(apartment, photo.getApartment());
    }
}
