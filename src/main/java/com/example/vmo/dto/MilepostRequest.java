package com.example.vmo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilepostRequest {
    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private BigDecimal amount;
    private String description;
    private Boolean status;
    private Long sowId;
}