package com.github.manimovassagh.immo_finder.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rent_apartment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentApartment {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "additional_costs", nullable = false)
    private BigDecimal additionalCosts;

    @Transient
    public BigDecimal getTotalPrice() {
        if (basePrice == null) return additionalCosts;
        if (additionalCosts == null) return basePrice;
        return basePrice.add(additionalCosts);
    }

    @Column(nullable = false)
    private int rooms;

    @Column(name = "furnished", nullable = false)
    private boolean furnished;

    @Column(name = "has_parking", nullable = false)
    private boolean hasParking;

    @Column(name = "has_balcony", nullable = false)
    private boolean hasBalcony;

    @Column(name = "has_storage", nullable = false)
    private boolean hasStorage;

    private double size;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}