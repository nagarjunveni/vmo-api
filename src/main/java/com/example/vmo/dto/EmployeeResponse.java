package com.example.vmo.dto;

import com.example.vmo.enums.EmploymentStatus;
import com.example.vmo.enums.EmploymentType;
import com.example.vmo.enums.Gender;
import com.example.vmo.enums.WorkLocation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String contactNumber;
    private String email;
    private Long vendorId;
    private String profilePicture; // Base64 encoded string

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Gender gender;
    private WorkLocation workLocation;
    private EmploymentType employmentType;
    private boolean isFreelancer;
    private String department;
    private EmploymentStatus employmentStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private String location;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean status;

    // Optional field for expanded information
    private VendorResponse vendor;
}