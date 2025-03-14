package com.example.vmo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorRequest {
    private String employeeIdentificationNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String companyName;
    private String location;
    private float rating;
    private float commission;
}