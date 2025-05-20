package com.github.manimovassagh.immo_finder.rent_service.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ApartmentServiceImpl.class);

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
            
            // Get User ID from Security Context
            String userId = SecurityUtils.getCurrentUserId();
            if (userId == null) {
                logger.error("User ID not found in security context. This should not happen for an authenticated request.");
                throw new IllegalStateException("User ID could not be determined from the security context.");
            }
            apartment.setUserId(userId);
            
            // Get the address from the apartment
            Address address = apartment.getAddress();
            
            logger.info("Checking for existing address: street='{}', number='{}', postalCode='{}', city='{}', country='{}'", address.getStreet(), address.getNumber(), address.getPostalCode(), address.getCity(), address.getCountry());
            // Check if address already exists
            Optional<Address> existingAddress = addressRepository.findByStreetAndNumberAndPostalCodeAndCityAndCountry(
                address.getStreet().trim(),
                address.getNumber().trim(),
                address.getPostalCode().trim(),
                address.getCity().trim(),
                address.getCountry().trim()
            );
            logger.info("Existing address found: {}", existingAddress.isPresent());
            if (existingAddress.isPresent()) {
                throw new IllegalArgumentException("An apartment with this address already exists: " + 
                    address.getStreet() + " " + address.getNumber() + ", " + 
                    address.getPostalCode() + " " + address.getCity() + ", " + 
                    address.getCountry());
            }
            
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