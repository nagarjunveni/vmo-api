package com.example.vmo.dto;

import com.example.vmo.enums.ProjectState;
import com.example.vmo.enums.StatementOfWorkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOfWorkRequest {
    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private StatementOfWorkType type;
    private BigDecimal fixedBidAmount;
    private ProjectState projectState;

    private Long lineManagerId;
    private Long escalationManagerId;
    private Long vendorEscalationManagerId;
    private Long authorizedSignatureId;
}