package com.github.manimovassagh.immo_finder.rent_service.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.manimovassagh.immo_finder.rent_service.model.entity.ApartmentForRent;
import com.github.manimovassagh.immo_finder.rent_service.repository.ApartmentRepository;
import com.github.manimovassagh.immo_finder.rent_service.service.ApartmentService;

@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public ApartmentForRent createApartment(ApartmentForRent apartment) {
        // Set default values
        apartment.setIsAvailable(true);
        apartment.setCreatedAt(LocalDateTime.now());
        apartment.setUpdatedAt(LocalDateTime.now());
        
        return apartmentRepository.save(apartment);
    }
} 