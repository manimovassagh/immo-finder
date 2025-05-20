package com.github.manimovassagh.immo_finder.rent_service.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.manimovassagh.immo_finder.rent_service.model.dto.ApartmentDTO;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.Address;
import com.github.manimovassagh.immo_finder.rent_service.model.entity.Apartment;
import com.github.manimovassagh.immo_finder.rent_service.model.mapper.ApartmentMapper;
import com.github.manimovassagh.immo_finder.rent_service.repository.AddressRepository;
import com.github.manimovassagh.immo_finder.rent_service.repository.ApartmentRepository;
import com.github.manimovassagh.immo_finder.rent_service.service.ApartmentService;
import com.github.manimovassagh.immo_finder.rent_service.util.SecurityUtils;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final AddressRepository addressRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, AddressRepository addressRepository) {
        this.apartmentRepository = apartmentRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public ApartmentDTO createApartment(ApartmentDTO apartmentDTO) {
        try {
            // Create apartment entity
            Apartment apartment = ApartmentMapper.toEntity(apartmentDTO);
            apartment.setUserId(SecurityUtils.getCurrentUserId());
            
            // Get the address from the apartment
            Address address = apartment.getAddress();
            
            // Save the address first (generates ID)
            address = addressRepository.save(address);
            
            // Set up the bidirectional relationship
            address.setApartment(apartment);
            apartment.setAddress(address);
            
            // Save the apartment
            apartment = apartmentRepository.save(apartment);
            
            // Return the DTO
            return ApartmentMapper.toDTO(apartment);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
} 