package com.example.vmo.dto;

import com.example.vmo.enums.PositionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedDate;
}