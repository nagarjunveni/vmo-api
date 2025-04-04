package com.example.vmo.dto;

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
public class MilepostResponse {
    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private BigDecimal amount;
    private String description;
    private Long sowId;
    private boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedDate;

    // Optional expanded information
    private StatementOfWorkResponse statementOfWork;
}