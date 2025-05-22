package com.github.manimovassagh.immo_finder.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.manimovassagh.immo_finder.dtos.CreateApartmentRequest;
import com.github.manimovassagh.immo_finder.models.Address;
import com.github.manimovassagh.immo_finder.models.RentApartment;
import com.github.manimovassagh.immo_finder.repositories.RentApartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentApartmentService {

    private final RentApartmentRepository rentApartmentRepository;

public ResponseEntity<?> createApartment(CreateApartmentRequest request) {



     // Check if address exists
        if (request.getAddress() != null) {
            Address address = request.getAddress();
            // Set default country if null
            if (address.getCountry() == null) {
                address.setCountry("DE");
            }
            boolean addressExists = rentApartmentRepository.existsByAddress(
                address.getStreet(),
                address.getHouseNumber(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry()
            );

            if (addressExists) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Address already exists");
                errorResponse.put("message", "An apartment with this address is already listed");
                return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
            }
        }

        // Create a new RentApartment from the request
        RentApartment apartment = RentApartment.builder()
                .userId(request.getUserId() != null ? request.getUserId() : UUID.randomUUID())
                .title(request.getTitle())
                .description(request.getDescription())
                .basePrice(request.getBasePrice())
                .additionalCosts(request.getAdditionalCosts())
                .rooms(request.getRooms())
                .furnished(request.isFurnished())
                .hasParking(request.isHasParking())
                .hasBalcony(request.isHasBalcony())
                .hasStorage(request.isHasStorage())
                .size(request.getSize() != null ? request.getSize() : 0.0)
                .floor(request.getFloor())
                .totalFloors(request.getTotalFloors())
                .availableFrom(request.getAvailableFrom())
                .energyCertificate(request.getEnergyCertificate())
                .yearBuilt(request.getYearBuilt())
                .propertyType(request.getPropertyType())
                .petsAllowed(request.getPetsAllowed())
                .heatingType(request.getHeatingType())
                .elevator(request.getElevator())
                .barrierFree(request.getBarrierFree())
                .address(request.getAddress())
                .build();

        // Save the apartment
        RentApartment savedApartment = rentApartmentRepository.save(apartment);
        return new ResponseEntity<>(savedApartment, HttpStatus.CREATED);
}

}
