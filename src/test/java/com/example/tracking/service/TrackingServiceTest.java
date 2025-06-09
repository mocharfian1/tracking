package com.example.tracking.service;

import com.example.tracking.exception.ResponseEntityException;
import com.example.tracking.model.TrackingNumber;
import com.example.tracking.model.dto.TrackingRequestDTO;
import com.example.tracking.model.entity.TrackingNumberCounterEntity;
import com.example.tracking.model.entity.TrackingRequest;
import com.example.tracking.repository.TrackingNumberCounterRepository;
import com.example.tracking.repository.TrackingRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackingServiceTest {

    @InjectMocks
    private TrackingService trackingService;

    @Mock
    private TrackingNumberCounterRepository counterRepository;

    @Mock
    private TrackingRequestRepository trackingRequestRepository;

    @Mock
    private BindingResult bindingResult;

    @Test
    void testGenerateTrackingNumber_SuccessCreateNew() {
        TrackingRequestDTO trackingRequestDTO = new TrackingRequestDTO();
        trackingRequestDTO.setOriginCountryId("US");
        trackingRequestDTO.setDestinationCountryId("ID");
        trackingRequestDTO.setCustomerId("customer1");
        trackingRequestDTO.setCustomerName("Customer Name");
        trackingRequestDTO.setCustomerSlug("customer-slug");
        trackingRequestDTO.setWeight(10.5);

        when(counterRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<TrackingNumber> response = trackingService.generateTrackingNumber(trackingRequestDTO, bindingResult);

        verify(counterRepository, times(1)).save(any(TrackingNumberCounterEntity.class));
        verify(trackingRequestRepository, times(1)).save(any(TrackingRequest.class));

        assertNotNull(response, response.getBody().getTrackingNumber());
    }

    @Test
    void testGenerateTrackingNumber_Success() {
        TrackingRequestDTO trackingRequestDTO = new TrackingRequestDTO();
        trackingRequestDTO.setOriginCountryId("US");
        trackingRequestDTO.setDestinationCountryId("ID");
        trackingRequestDTO.setCustomerId("customer1");
        trackingRequestDTO.setCustomerName("Customer Name");
        trackingRequestDTO.setCustomerSlug("customer-slug");
        trackingRequestDTO.setWeight(10.5);

        TrackingNumberCounterEntity mockCounterEntity = new TrackingNumberCounterEntity();
        mockCounterEntity.setId(1L);
        mockCounterEntity.setCounter(5L);
        mockCounterEntity.setLastTimestamp("202306091200");

        when(counterRepository.findById(1L)).thenReturn(Optional.of(mockCounterEntity));
        when(counterRepository.save(any(TrackingNumberCounterEntity.class))).thenReturn(mockCounterEntity);

        String generatedTrackingNumber = "USID0000000005202306091200";
        TrackingRequest mockTrackingRequest = new TrackingRequest();
        mockTrackingRequest.setTrackingNumber(generatedTrackingNumber);
        mockTrackingRequest.setOriginCountryId("US");
        mockTrackingRequest.setDestinationCountryId("ID");
        mockTrackingRequest.setCustomerId("customer1");

        when(trackingRequestRepository.save(any(TrackingRequest.class))).thenReturn(mockTrackingRequest);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<TrackingNumber> response = trackingService.generateTrackingNumber(trackingRequestDTO, bindingResult);

        verify(counterRepository, times(1)).save(any(TrackingNumberCounterEntity.class));
        verify(trackingRequestRepository, times(1)).save(any(TrackingRequest.class));

        assertNotNull(generatedTrackingNumber, response.getBody().getTrackingNumber());
    }

    @Test
    void testGenerateTrackingNumber_WithValidationErrors() {
        TrackingRequestDTO trackingRequestDTO = new TrackingRequestDTO();
        trackingRequestDTO.setOriginCountryId("US");
        trackingRequestDTO.setDestinationCountryId("ID");
        trackingRequestDTO.setCustomerId(null);

        ObjectError error = new ObjectError("customerId", "Customer ID is required");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(error));

        ResponseEntityException thrown = assertThrows(
                ResponseEntityException.class,
                () -> trackingService.generateTrackingNumber(trackingRequestDTO, bindingResult)
        );

        assertEquals("[Customer ID is required]", thrown.getMessage());
    }

}
