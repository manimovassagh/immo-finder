package com.github.manimovassagh.immo_finder.rent_service.model.mapper;

import com.github.manimovassagh.immo_finder.rent_service.model.dto.AddressDTO;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.Address;

public class AddressMapper {
    public static AddressDTO toDTO(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry()
        );
    }

    public static Address toEntity(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }
        Address address = new Address();
        address.setId(addressDTO.id());
        address.setStreet(addressDTO.street());
        address.setNumber(addressDTO.number());
        address.setPostalCode(addressDTO.postalCode());
        address.setCity(addressDTO.city());
        address.setCountry(addressDTO.country());
        return address;
    }
} 