package com.github.manimovassagh.immo_finder.rent_service.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void testAddressGettersAndSetters() {
        Address address = new Address();
        UUID id = UUID.randomUUID();
        String street = "Test Street";
        String houseNumber = "123";
        String postalCode = "12345";
        String city = "Test City";
        String country = "DE";

        address.setId(id);
        address.setStreet(street);
        address.setHouseNumber(houseNumber);
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setCountry(country);

        assertEquals(id, address.getId());
        assertEquals(street, address.getStreet());
        assertEquals(houseNumber, address.getHouseNumber());
        assertEquals(postalCode, address.getPostalCode());
        assertEquals(city, address.getCity());
        assertEquals(country, address.getCountry());
    }
} 