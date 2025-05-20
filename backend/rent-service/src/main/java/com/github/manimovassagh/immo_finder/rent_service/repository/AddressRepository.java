package com.github.manimovassagh.immo_finder.rent_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.manimovassagh.immo_finder.rent_service.model.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
} 