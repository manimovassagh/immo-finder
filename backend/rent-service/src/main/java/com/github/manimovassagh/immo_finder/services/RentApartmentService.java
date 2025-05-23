package com.github.manimovassagh.immo_finder.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private static final Logger logger = LoggerFactory.getLogger(RentApartmentService.class);
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

    /**
     * Retrieves apartments with pagination support.
     * 
     * @param pageable pagination information including page number, size, and sorting
     * @return ResponseEntity containing either a page of apartments or an error message
     */
    public ResponseEntity<Page<RentApartment>> getApartments(Pageable pageable) {
        logger.info("Fetching apartments with pagination: {}", pageable);
        try {
            Page<RentApartment> apartments = rentApartmentRepository.findAll(pageable);
            logger.info("Successfully retrieved {} apartments from page {}", 
                    apartments.getNumberOfElements(), pageable.getPageNumber());
            return new ResponseEntity<>(apartments, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching apartments: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all apartments without pagination.
     * Note: This method should be used with caution for large datasets.
     * Consider using the paginated version {@link #getApartments(Pageable)} instead.
     * 
     * @return ResponseEntity containing either a list of all apartments or an error message
     */
    public ResponseEntity<List<RentApartment>> getApartments() {
        logger.info("Fetching all apartments without pagination");
        try {
            List<RentApartment> apartments = rentApartmentRepository.findAll();
            logger.info("Successfully retrieved {} apartments", apartments.size());
            return new ResponseEntity<>(apartments, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching apartments: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
