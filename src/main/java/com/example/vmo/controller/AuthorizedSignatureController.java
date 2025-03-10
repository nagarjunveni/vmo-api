package com.example.vmo.controller;

import com.example.vmo.dto.AuthorizedSignatureRequest;
import com.example.vmo.dto.AuthorizedSignatureResponse;
import com.example.vmo.service.AuthorizedSignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/authorized-signatures")
@RequiredArgsConstructor
public class AuthorizedSignatureController {

    private final AuthorizedSignatureService authorizedSignatureService;

    /**
     * Create a new authorized signature
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthorizedSignatureResponse> createAuthorizedSignature(
            @ModelAttribute AuthorizedSignatureRequest request) throws IOException {
        AuthorizedSignatureResponse response = authorizedSignatureService.createAuthorizedSignature(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing authorized signature
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthorizedSignatureResponse> updateAuthorizedSignature(
            @PathVariable Long id,
            @ModelAttribute AuthorizedSignatureRequest request) throws IOException {
        AuthorizedSignatureResponse response = authorizedSignatureService.updateAuthorizedSignature(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get an authorized signature by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorizedSignatureResponse> getAuthorizedSignatureById(@PathVariable Long id) {
        AuthorizedSignatureResponse response = authorizedSignatureService.getAuthorizedSignatureById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active authorized signatures
     */
    @GetMapping
    public ResponseEntity<List<AuthorizedSignatureResponse>> getAllActiveAuthorizedSignatures() {
        List<AuthorizedSignatureResponse> responses = authorizedSignatureService.getAllActiveAuthorizedSignatures();
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete an authorized signature
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorizedSignature(@PathVariable Long id) {
        authorizedSignatureService.deleteAuthorizedSignature(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Download digital signature file
     */
    @GetMapping("/{id}/digital-signature")
    public ResponseEntity<byte[]> downloadDigitalSignature(@PathVariable Long id) {
        byte[] signatureBytes = authorizedSignatureService.getDigitalSignature(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "signature.png");

        return new ResponseEntity<>(signatureBytes, headers, HttpStatus.OK);
    }
}