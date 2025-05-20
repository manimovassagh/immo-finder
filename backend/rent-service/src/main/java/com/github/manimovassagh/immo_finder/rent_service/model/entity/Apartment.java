package com.github.manimovassagh.immo_finder.rent_service.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @Size(min = 1, max = 255)
    private String title;

    @Column(nullable = false)
    @Size(min = 1, max = 1000)
    private String description;

    @Column(nullable = false)
    @Min(0)
    private BigDecimal coldRent;  // Kaltmiete

    @Column(nullable = false)
    @Min(0)
    private BigDecimal additionalCosts;  // Nebenkosten

    @Column(nullable = false)
    @Min(0)
    private BigDecimal totalPrice;  // Total price (coldRent + additionalCosts)

    @Column(nullable = false)
    @Min(1)
    private Integer numberOfRooms;

    @Column(nullable = false)
    @Min(1)
    private Integer numberOfBathrooms;

    @Column(nullable = false)
    @Min(1)
    private BigDecimal area;

    @Column(nullable = false)
    private Boolean isAvailable;

    @Column(nullable = false)
    private String userId;  // Keycloak user ID

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", referencedColumnName = "id", unique = true)
    private Address apartmentAddress;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "apartment_photo_paths", joinColumns = @JoinColumn(name = "apartment_id"))
    @Column(name = "photo_path")
    private List<String> photoPaths = new ArrayList<>();

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getColdRent() {
        return coldRent;
    }

    public void setColdRent(BigDecimal coldRent) {
        this.coldRent = coldRent;
        calculateTotalPrice();
    }

    public BigDecimal getAdditionalCosts() {
        return additionalCosts;
    }

    public void setAdditionalCosts(BigDecimal additionalCosts) {
        this.additionalCosts = additionalCosts;
        calculateTotalPrice();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    private void calculateTotalPrice() {
        if (this.coldRent != null && this.additionalCosts != null) {
            this.totalPrice = this.coldRent.add(this.additionalCosts);
        }
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(Integer numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Address getApartmentAddress() {
        return apartmentAddress;
    }

    public void setApartmentAddress(Address apartmentAddress) {
        this.apartmentAddress = apartmentAddress;
    }

    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public void addPhotoPath(String photoPath) {
        if (this.photoPaths == null) {
            this.photoPaths = new ArrayList<>();
        }
        this.photoPaths.add(photoPath);
    }
} 