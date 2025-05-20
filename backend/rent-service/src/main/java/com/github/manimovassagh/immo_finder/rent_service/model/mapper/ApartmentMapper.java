package com.github.manimovassagh.immo_finder.rent_service.model.mapper;

import com.github.manimovassagh.immo_finder.rent_service.model.dto.ApartmentDTO;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.Apartment;

public class ApartmentMapper {
    public static ApartmentDTO toDTO(Apartment apartment) {
        if (apartment == null) {
            return null;
        }
        return new ApartmentDTO(
            apartment.getId(),
            apartment.getTitle(),
            apartment.getDescription(),
            apartment.getColdRent(),
            apartment.getAdditionalCosts(),
            apartment.getTotalPrice(),
            apartment.getNumberOfRooms(),
            apartment.getNumberOfBathrooms(),
            apartment.getArea(),
            apartment.getIsAvailable(),
            apartment.getUserId(),
            AddressMapper.toDTO(apartment.getApartmentAddress()),
            apartment.getPhotoPaths()
        );
    }

    public static Apartment toEntity(ApartmentDTO dto) {
        if (dto == null) {
            return null;
        }
        Apartment apartment = new Apartment();
        apartment.setId(dto.id());
        apartment.setTitle(dto.title());
        apartment.setDescription(dto.description());
        apartment.setColdRent(dto.coldRent());
        apartment.setAdditionalCosts(dto.additionalCosts());
        apartment.setNumberOfRooms(dto.numberOfRooms());
        apartment.setNumberOfBathrooms(dto.numberOfBathrooms());
        apartment.setArea(dto.area());
        apartment.setIsAvailable(dto.isAvailable());
        apartment.setUserId(dto.userId());
        apartment.setApartmentAddress(AddressMapper.toEntity(dto.address()));
        apartment.setPhotoPaths(dto.photoPaths());
        return apartment;
    }
} 