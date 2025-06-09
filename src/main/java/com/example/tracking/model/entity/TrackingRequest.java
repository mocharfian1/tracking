package com.example.tracking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_country_id")
    private String originCountryId;

    @Column(name = "destination_country_id")
    private String destinationCountryId;

    private String customerId;

    private String customerName;
    private String customerSlug;
    private Double weight;
    private String trackingNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
