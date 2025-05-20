package com.github.manimovassagh.immo_finder.rent_service.model.mapper;

import com.github.manimovassagh.immo_finder.rent_service.model.dto.ApartmentDTO;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.Apartment;

public class ApartmentMapper {
    public static ApartmentDTO toDTO(Apartment apartment) {
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
                AddressMapper.toDTO(apartment.getAddress())
        );
    }

    public static Apartment toEntity(ApartmentDTO apartmentDTO) {
        Apartment apartment = new Apartment();
        apartment.setId(apartmentDTO.id());
        apartment.setTitle(apartmentDTO.title());
        apartment.setDescription(apartmentDTO.description());
        apartment.setColdRent(apartmentDTO.coldRent());
        apartment.setAdditionalCosts(apartmentDTO.additionalCosts());
        apartment.setNumberOfRooms(apartmentDTO.numberOfRooms());
        apartment.setNumberOfBathrooms(apartmentDTO.numberOfBathrooms());
        apartment.setArea(apartmentDTO.area());
        apartment.setIsAvailable(apartmentDTO.isAvailable());
        apartment.setAddress(AddressMapper.toEntity(apartmentDTO.address()));
        return apartment;
    }
} 