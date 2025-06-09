package com.example.tracking.helper;

import com.example.tracking.model.dto.TrackingRequestDTO;
import org.springframework.validation.BindingResult;

public class ValidationHelper {

    public static void validateTrackingRequestDTO(TrackingRequestDTO trackingRequestDTO, BindingResult bindingResult) {
        if (trackingRequestDTO.getOriginCountryId().length() != 2) {
            bindingResult.rejectValue("originCountryId", "countryCode.invalid", "Origin Country ID must be exactly 2 characters long.");
        }
        if (trackingRequestDTO.getDestinationCountryId().length() != 2) {
            bindingResult.rejectValue("destinationCountryId", "countryCode.invalid", "Destination Country ID must be exactly 2 characters long.");
        }

        if (trackingRequestDTO.getCustomerId() == null || trackingRequestDTO.getCustomerId().isEmpty()) {
            bindingResult.rejectValue("customerId", "customerId.required", "Customer ID is required.");
        }

        if (trackingRequestDTO.getCustomerName() == null || trackingRequestDTO.getCustomerName().isEmpty()) {
            bindingResult.rejectValue("customerName", "customerName.required", "Customer Name is required.");
        }

        if (trackingRequestDTO.getWeight() == null || trackingRequestDTO.getWeight() <= 0) {
            bindingResult.rejectValue("weight", "weight.invalid", "Weight must be greater than 0.");
        }
    }

    public static String generateTrackingNumber(String originCountryId, String destinationCountryId, Long counter, String timestamp) {
        String originCountryIdPrefix = originCountryId.length() >= 2 ? originCountryId.substring(0, 2).toUpperCase() : "XX";
        String destinationCountryIdPrefix = destinationCountryId.length() >= 2 ? destinationCountryId.substring(0, 2).toUpperCase() : "XX";

        return originCountryIdPrefix + destinationCountryIdPrefix + String.format("%010d", counter) + timestamp;
    }
}
