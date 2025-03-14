package com.example.vmo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponse {
    private Long id;
    private String employeeIdentificationNumber;
    private String companyName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String location;
    private float rating;
    private float commission;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean status;
}