package com.example.vmo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.vmo.enums.PositionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal amount;
    private BigDecimal hourlyRate;
    private BigDecimal monthlyRate;
    private String skills;
    private String expertise;
    private PositionType type;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean status;
}