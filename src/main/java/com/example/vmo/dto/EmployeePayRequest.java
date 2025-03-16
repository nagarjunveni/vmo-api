package com.example.vmo.dto;

import com.example.vmo.enums.PayType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePayRequest {
    private PayType type;
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    private Boolean status;
    private Long employeeId;
    private BigDecimal hourlyRate;
    private BigDecimal monthlyRate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDate;
}