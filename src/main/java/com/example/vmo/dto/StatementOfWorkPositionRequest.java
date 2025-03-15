package com.example.vmo.dto;

import com.example.vmo.enums.PositionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOfWorkPositionRequest {
    private Long id;
    private Long sowId;
    private Long positionId;
    private PositionType type;
    private Boolean status;
}