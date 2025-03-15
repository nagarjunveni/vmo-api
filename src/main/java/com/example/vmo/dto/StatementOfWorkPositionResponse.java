package com.example.vmo.dto;

import com.example.vmo.enums.PositionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOfWorkPositionResponse {
    private Long id;
    private Long sowId;
    private Long positionId;
    private PositionType type;
    private boolean status;

    // Optional fields for expanded information
    private StatementOfWorkResponse statementOfWork;
    private PositionResponse position;
}