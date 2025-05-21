package com.github.manimovassagh.immo_finder.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.manimovassagh.immo_finder.models.RentApartment;

@Repository
public interface RentApartmentRepository extends JpaRepository<RentApartment, UUID> {
}