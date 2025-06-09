package com.example.tracking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tracking_number_counter")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingNumberCounterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long counter;
    private String lastTimestamp;
}
