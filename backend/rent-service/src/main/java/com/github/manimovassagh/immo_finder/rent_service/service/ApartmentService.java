package com.github.manimovassagh.immo_finder.rent_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.manimovassagh.immo_finder.rent_service.model.Apartment;
import com.github.manimovassagh.immo_finder.rent_service.repository.ApartmentRepository;

@Service
@Transactional
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public Apartment createApartment(Apartment apartment) {
        apartment.setIsAvailable(true);
        return apartmentRepository.save(apartment);
    }
} 