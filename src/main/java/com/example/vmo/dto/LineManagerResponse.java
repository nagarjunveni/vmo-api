package com.example.vmo.dto;

import com.example.vmo.enums.LineManagerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineManagerResponse {
    private Long id;
    private LineManagerType type;
    private String typeDisplayName;
    private String department;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean status;
}