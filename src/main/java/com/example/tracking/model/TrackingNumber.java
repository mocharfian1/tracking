package com.example.tracking.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TrackingNumber {
    private String trackingNumber;
    private LocalDateTime createdAt;
    private String originCountryId;
    private String destinationCountryId;
    private Double weight;
    private String customerId;
    private String customerName;
    private String customerSlug;
}
