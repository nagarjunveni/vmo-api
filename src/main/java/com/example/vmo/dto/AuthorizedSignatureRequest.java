package com.example.vmo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizedSignatureRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;
    private MultipartFile digitalSignature;
}