package com.example.tracking.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

@Data
public class TrackingRequestDTO {

    @NotNull(message = "Origin Country ID is required")
    @Size(min = 2, max = 2, message = "Origin Country ID must be exactly 2 characters long")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Origin Country ID must be in ISO 3166-1 alpha-2 format")
    private String originCountryId;

    @NotNull(message = "Destination Country ID is required")
    @Size(min = 2, max = 2, message = "Destination Country ID must be exactly 2 characters long")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Destination Country ID must be in ISO 3166-1 alpha-2 format")
    private String destinationCountryId;

    @NotNull(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Customer Name is required")
    private String customerName;

    @NotNull(message = "Customer Slug is required")
    private String customerSlug;

    private Double weight = 0.0;
}
