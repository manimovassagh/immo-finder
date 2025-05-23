package com.github.manimovassagh.immo_finder.controllers;

import com.github.manimovassagh.immo_finder.models.RentApartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.github.manimovassagh.immo_finder.dtos.CreateApartmentRequest;
import com.github.manimovassagh.immo_finder.services.RentApartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
@Validated
@RequiredArgsConstructor
public class RentApartmentController {

    private final RentApartmentService rentApartmentService;

    @PostMapping
    public ResponseEntity<?> createApartment(@Valid @RequestBody CreateApartmentRequest request) {
        return rentApartmentService.createApartment(request);
    }


    /**
     * Retrieves all apartments with pagination support.
     * 
     * @param pageable pagination information (page, size, sort)
     * @return ResponseEntity containing a page of apartments
     */
    @GetMapping
    public ResponseEntity<Page<RentApartment>> getAllApartments(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return rentApartmentService.getApartments(pageable);
    }

    /**
     * Retrieves all apartments without pagination.
     * Note: This endpoint should be used with caution for large datasets.
     * Consider using the paginated version instead.
     * @return ResponseEntity containing a list of all apartments
     */
    @GetMapping("/all")
    public ResponseEntity<List<RentApartment>> getAllApartmentsWithoutPagination() {
        return rentApartmentService.getApartments();
    }

}
