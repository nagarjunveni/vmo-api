package com.example.vmo.dto;

import com.example.vmo.enums.LineManagerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineManagerRequest {
    private LineManagerType type;
    private String department;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;
}