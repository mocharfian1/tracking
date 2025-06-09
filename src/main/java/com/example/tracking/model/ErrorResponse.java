package com.example.tracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String status;
    private List<String> message;
}
