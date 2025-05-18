package com.github.manimovassagh.immo_finder.rent_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.manimovassagh.immo_finder.rent_service.model.entity.Address;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.ApartmentForRent;
import com.github.manimovassagh.immo_finder.rent_service.repository.AddressRepository;
import com.github.manimovassagh.immo_finder.rent_service.repository.ApartmentRepository;

@Service
@Transactional
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final AddressRepository addressRepository;

    public ApartmentService(ApartmentRepository apartmentRepository, AddressRepository addressRepository) {
        this.apartmentRepository = apartmentRepository;
        this.addressRepository = addressRepository;
    }

    public ApartmentForRent createApartment(ApartmentForRent apartment) {
        // Save address first
        Address savedAddress = addressRepository.save(apartment.getAddress());
        apartment.setAddress(savedAddress);
        
        // Set availability and save apartment
        apartment.setIsAvailable(true);
        return apartmentRepository.save(apartment);
    }
} 