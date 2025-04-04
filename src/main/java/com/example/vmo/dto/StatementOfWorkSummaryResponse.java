package com.example.vmo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOfWorkSummaryResponse {
    private Long id;
    private String statementOfWorkId;
    private String name;
    private Long lineManagerId;
    private String lineManagerName;
    private String csxEscalationManagerName;
    private String compnovaEscalationManagerName;
    private Long totalPositions;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String projectState;
    private boolean status;
}