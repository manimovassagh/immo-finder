package com.github.manimovassagh.immo_finder.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.manimovassagh.immo_finder.models.RentApartment;

@Repository
public interface RentApartmentRepository extends JpaRepository<RentApartment, UUID> {

    @Query("SELECT COUNT(r) > 0 FROM RentApartment r JOIN r.address a WHERE " +
           "a.street = :street AND " +
           "a.houseNumber = :houseNumber AND " +
           "a.postalCode = :postalCode AND " +
           "a.city = :city AND " +
           "a.country = :country")
    boolean existsByAddress(
            @Param("street") String street,
            @Param("houseNumber") String houseNumber,
            @Param("postalCode") String postalCode,
            @Param("city") String city,
            @Param("country") String country);
}
