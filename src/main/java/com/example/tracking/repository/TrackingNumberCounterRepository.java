package com.example.tracking.repository;

import com.example.tracking.model.entity.TrackingNumberCounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingNumberCounterRepository extends JpaRepository<TrackingNumberCounterEntity, Long> {
}
