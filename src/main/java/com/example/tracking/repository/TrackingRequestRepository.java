package com.example.tracking.repository;

import com.example.tracking.model.entity.TrackingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRequestRepository extends JpaRepository<TrackingRequest, Long> {
}
