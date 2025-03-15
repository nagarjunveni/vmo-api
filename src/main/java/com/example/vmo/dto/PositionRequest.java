package com.example.vmo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionRequest {
    private String title;
    private String description;
    private BigDecimal amount;
    private BigDecimal hourlyRate;
    private BigDecimal monthlyRate;
    private String skills;
    private String expertise;
    private Boolean status;
}