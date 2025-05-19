package com.github.manimovassagh.immo_finder.rent_service.util;

import org.springframework.stereotype.Component;

import com.github.manimovassagh.immo_finder.rent_service.model.dto.ApartmentForRentDTO;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.ApartmentForRent;

@Component
public class ApartmentMapper {

    public ApartmentForRent toEntity(ApartmentForRentDTO dto) {
        if (dto == null) {
            return null;
        }

        ApartmentForRent entity = new ApartmentForRent();
        entity.setId(dto.id());
        entity.setUserId(dto.userId());
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setPricePerMonth(dto.pricePerMonth());
        entity.setArea(dto.area());
        entity.setRooms(dto.rooms());
        entity.setIsAvailable(dto.isAvailable());
        entity.setCreatedAt(dto.createdAt());
        entity.setUpdatedAt(dto.updatedAt());

        return entity;
    }

    public ApartmentForRentDTO toDTO(ApartmentForRent entity) {
        if (entity == null) {
            return null;
        }

        return new ApartmentForRentDTO(
            entity.getId(),
            entity.getUserId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getPricePerMonth(),
            entity.getArea(),
            entity.getRooms(),
            entity.getIsAvailable(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
} 