package com.example.tracking.service;

import com.example.tracking.exception.ResponseEntityException;
import com.example.tracking.model.ErrorResponse;
import com.example.tracking.model.TrackingNumber;
import com.example.tracking.model.entity.TrackingNumberCounterEntity;
import com.example.tracking.model.dto.TrackingRequestDTO;
import com.example.tracking.model.entity.TrackingRequest;
import com.example.tracking.repository.TrackingNumberCounterRepository;
import com.example.tracking.repository.TrackingRequestRepository;
import com.example.tracking.helper.ValidationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrackingService {

    private static final Logger logger = LoggerFactory.getLogger(TrackingService.class);

    private final TrackingNumberCounterRepository counterRepository;
    private final TrackingRequestRepository trackingRequestRepository;

    public TrackingService(TrackingNumberCounterRepository counterRepository, TrackingRequestRepository trackingRequestRepository) {
        this.counterRepository = counterRepository;
        this.trackingRequestRepository = trackingRequestRepository;
    }

    public ResponseEntity<TrackingNumber> generateTrackingNumber(TrackingRequestDTO trackingRequestDTO, BindingResult bindingResult) {
        logger.info("Received request to generate tracking number for customer: {}", trackingRequestDTO.getCustomerName());

        List<String> errorMessages = validateTrackingRequest(trackingRequestDTO, bindingResult);
        if (!errorMessages.isEmpty()) {
            logger.warn("Validation failed: {}", errorMessages);
            throw new ResponseEntityException(ErrorResponse.builder().status("Error").message(errorMessages).build());
        }

        TrackingNumberCounterEntity counterRecord = getOrCreateCounterRecord();
        resetCounterIfTimestampChanged(counterRecord);

        String trackingNumber = generateTrackingNumber(counterRecord, trackingRequestDTO);
        logger.info("Generated tracking number: {}", trackingNumber);

        incrementCounter(counterRecord);
        logger.info("Counter incremented to: {}", counterRecord.getCounter());

        TrackingNumber tracking = buildTrackingNumber(trackingRequestDTO, trackingNumber);
        logger.info("TrackingNumber object built: {}", tracking);

        saveTrackingRequest(trackingRequestDTO, trackingNumber);
        logger.info("Tracking request saved to repository");

        return new ResponseEntity<>(tracking, HttpStatus.CREATED);
    }

    private List<String> validateTrackingRequest(TrackingRequestDTO trackingRequestDTO, BindingResult bindingResult) {
        logger.debug("Validating tracking request DTO");
        List<String> errorMessages = new ArrayList<>();
        ValidationHelper.validateTrackingRequestDTO(trackingRequestDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
        }
        return errorMessages;
    }

    private TrackingNumberCounterEntity getOrCreateCounterRecord() {
        logger.debug("Retrieving counter record");
        return counterRepository.findById(1L).orElseGet(() -> {
            logger.info("Counter record not found, creating new");
            return createInitialCounterRecord();
        });
    }

    private TrackingNumberCounterEntity createInitialCounterRecord() {
        return TrackingNumberCounterEntity.builder()
                .id(1L)
                .counter(0L)
                .build();
    }

    private void resetCounterIfTimestampChanged(TrackingNumberCounterEntity counterRecord) {
        String currentTimestamp = getCurrentTimestamp();
        if (!currentTimestamp.equals(counterRecord.getLastTimestamp())) {
            logger.info("Timestamp changed. Resetting counter from {} to 0", counterRecord.getCounter());
            counterRecord.setCounter(0L);
        }
        counterRecord.setLastTimestamp(currentTimestamp);
    }

    private String generateTrackingNumber(TrackingNumberCounterEntity counterRecord, TrackingRequestDTO trackingRequestDTO) {
        logger.debug("Generating tracking number");
        String currentTimestamp = getCurrentTimestamp();
        return ValidationHelper.generateTrackingNumber(
                trackingRequestDTO.getOriginCountryId(),
                trackingRequestDTO.getDestinationCountryId(),
                counterRecord.getCounter(),
                currentTimestamp
        );
    }

    private void incrementCounter(TrackingNumberCounterEntity counterRecord) {
        counterRecord.setCounter(counterRecord.getCounter() + 1);
        counterRepository.save(counterRecord);
    }

    private TrackingNumber buildTrackingNumber(TrackingRequestDTO trackingRequestDTO, String trackingNumber) {
        return TrackingNumber.builder()
                .trackingNumber(trackingNumber)
                .createdAt(LocalDateTime.now())
                .originCountryId(trackingRequestDTO.getOriginCountryId())
                .destinationCountryId(trackingRequestDTO.getDestinationCountryId())
                .customerId(trackingRequestDTO.getCustomerId())
                .customerName(trackingRequestDTO.getCustomerName())
                .customerSlug(trackingRequestDTO.getCustomerSlug())
                .weight(trackingRequestDTO.getWeight())
                .build();
    }

    private void saveTrackingRequest(TrackingRequestDTO trackingRequestDTO, String trackingNumber) {
        TrackingRequest trackingRequest = TrackingRequest.builder()
                .originCountryId(trackingRequestDTO.getOriginCountryId())
                .destinationCountryId(trackingRequestDTO.getDestinationCountryId())
                .customerId(trackingRequestDTO.getCustomerId())
                .customerName(trackingRequestDTO.getCustomerName())
                .customerSlug(trackingRequestDTO.getCustomerSlug())
                .weight(trackingRequestDTO.getWeight())
                .trackingNumber(trackingNumber)
                .createdAt(LocalDateTime.now())
                .build();

        trackingRequestRepository.save(trackingRequest);
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }
}
