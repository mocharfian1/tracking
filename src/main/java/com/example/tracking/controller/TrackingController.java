package com.example.tracking.controller;

import com.example.tracking.model.dto.TrackingRequestDTO;
import com.example.tracking.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/next-tracking-number")
    public ResponseEntity<?> getNextTrackingNumber(
            @ModelAttribute @Valid TrackingRequestDTO trackingRequestDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }

        return trackingService.generateTrackingNumber(trackingRequestDTO, bindingResult);
    }
}
