package com.example.vmo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizedSignatureResponse {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;
    private boolean hasDigitalSignature;
    private boolean status;
    private Date createdAt;
    private Date updatedAt;
}