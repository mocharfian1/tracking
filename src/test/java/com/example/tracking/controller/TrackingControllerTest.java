package com.example.tracking.controller;

import com.example.tracking.model.TrackingNumber;
import com.example.tracking.model.dto.TrackingRequestDTO;
import com.example.tracking.service.TrackingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TrackingControllerTest {

    @Autowired
    private TrackingController trackingController;

    @MockBean
    private TrackingService trackingService;

    @Mock
    private BindingResult bindingResult;

    @Test
    void testGetNextTrackingNumber_Success() {
        TrackingRequestDTO trackingRequestDTO = new TrackingRequestDTO();
        trackingRequestDTO.setOriginCountryId("US");
        trackingRequestDTO.setDestinationCountryId("ID");
        trackingRequestDTO.setCustomerId("customer1");
        trackingRequestDTO.setCustomerName("Customer Name");
        trackingRequestDTO.setCustomerSlug("customer-slug");
        trackingRequestDTO.setWeight(10.5);

        ResponseEntity<TrackingNumber> expectedResponse = new ResponseEntity<>(TrackingNumber.builder().build(), HttpStatus.CREATED);

        when(trackingService.generateTrackingNumber(trackingRequestDTO, bindingResult))
                .thenReturn(expectedResponse);

        ResponseEntity<?> response = trackingController.getNextTrackingNumber(trackingRequestDTO, bindingResult);

        verify(trackingService, times(1)).generateTrackingNumber(trackingRequestDTO, bindingResult);

        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetNextTrackingNumber_WithValidationErrors() {
        TrackingRequestDTO trackingRequestDTO = new TrackingRequestDTO();
        trackingRequestDTO.setOriginCountryId("US");
        trackingRequestDTO.setDestinationCountryId("ID");

        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = trackingController.getNextTrackingNumber(trackingRequestDTO, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetNextTrackingNumber_WithValidationErrorsHandle() {
        TrackingRequestDTO trackingRequestDTO = new TrackingRequestDTO();
        trackingRequestDTO.setOriginCountryId("USD");
        trackingRequestDTO.setDestinationCountryId("ID");

        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = trackingController.getNextTrackingNumber(trackingRequestDTO, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
