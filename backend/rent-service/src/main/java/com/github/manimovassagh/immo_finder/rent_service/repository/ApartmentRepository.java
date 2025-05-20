package com.github.manimovassagh.immo_finder.rent_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.manimovassagh.immo_finder.rent_service.model.entity.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, UUID> {
}