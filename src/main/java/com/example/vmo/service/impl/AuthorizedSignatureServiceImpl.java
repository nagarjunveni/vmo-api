package com.example.vmo.service.impl;

import com.example.vmo.dto.AuthorizedSignatureRequest;
import com.example.vmo.dto.AuthorizedSignatureResponse;
import com.example.vmo.model.AuthorizedSignature;
import com.example.vmo.repository.AuthorizedSignatureRepository;
import com.example.vmo.service.AuthorizedSignatureService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizedSignatureServiceImpl implements AuthorizedSignatureService {

    private final AuthorizedSignatureRepository authorizedSignatureRepository;

    @Override
    @Transactional
    public AuthorizedSignatureResponse createAuthorizedSignature(AuthorizedSignatureRequest request)
            throws IOException {

        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getContactNumber() == null || request.getContactNumber().isEmpty()) {
            throw new IllegalArgumentException("Contact number is required");
        }
        if (authorizedSignatureRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }
        if (request.getDigitalSignature() == null || request.getDigitalSignature().isEmpty()) {
            throw new IllegalArgumentException("Digital signature is required");
        }

        AuthorizedSignature signature = new AuthorizedSignature();
        signature.setFirstName(request.getFirstName());
        signature.setMiddleName(request.getMiddleName());
        signature.setLastName(request.getLastName());
        signature.setEmail(request.getEmail());
        signature.setContactNumber(request.getContactNumber());
        signature.setStatus(true);

        // Handle digital signature file if provided
        if (request.getDigitalSignature() != null && !request.getDigitalSignature().isEmpty()) {
            signature.setDigitalSignature(request.getDigitalSignature().getBytes());
        }

        AuthorizedSignature savedSignature = authorizedSignatureRepository.save(signature);
        return mapToResponse(savedSignature);
    }

    @Override
    @Transactional
    public AuthorizedSignatureResponse updateAuthorizedSignature(Long id, AuthorizedSignatureRequest request)
            throws IOException {
        AuthorizedSignature signature = authorizedSignatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authorized Signature not found with id: " + id));

        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getContactNumber() == null || request.getContactNumber().isEmpty()) {
            throw new IllegalArgumentException("Contact number is required");
        }
        // Check if email already exists for another signature
        if (!signature.getEmail().equals(request.getEmail()) &&
                authorizedSignatureRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        signature.setFirstName(request.getFirstName());
        signature.setMiddleName(request.getMiddleName());
        signature.setLastName(request.getLastName());
        signature.setEmail(request.getEmail());
        signature.setContactNumber(request.getContactNumber());

        // Update digital signature only if a new one is provided
        if (request.getDigitalSignature() != null && !request.getDigitalSignature().isEmpty()) {
            signature.setDigitalSignature(request.getDigitalSignature().getBytes());
        }

        AuthorizedSignature updatedSignature = authorizedSignatureRepository.save(signature);
        return mapToResponse(updatedSignature);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorizedSignatureResponse getAuthorizedSignatureById(Long id) {
        AuthorizedSignature signature = authorizedSignatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authorized Signature not found with id: " + id));
        return mapToResponse(signature);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorizedSignatureResponse> getAllActiveAuthorizedSignatures() {
        return authorizedSignatureRepository.findByStatusTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAuthorizedSignature(Long id) {
        AuthorizedSignature signature = authorizedSignatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authorized Signature not found with id: " + id));
        signature.setStatus(false);
        authorizedSignatureRepository.save(signature);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getDigitalSignature(Long id) {
        AuthorizedSignature signature = authorizedSignatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authorized Signature not found with id: " + id));

        if (signature.getDigitalSignature() == null) {
            throw new EntityNotFoundException("Digital signature not found for id: " + id);
        }

        return signature.getDigitalSignature();
    }

    /**
     * Map entity to response DTO
     */
    private AuthorizedSignatureResponse mapToResponse(AuthorizedSignature signature) {
        AuthorizedSignatureResponse response = new AuthorizedSignatureResponse();
        response.setId(signature.getId());
        response.setFirstName(signature.getFirstName());
        response.setMiddleName(signature.getMiddleName());
        response.setLastName(signature.getLastName());
        response.setEmail(signature.getEmail());
        response.setContactNumber(signature.getContactNumber());
        response.setHasDigitalSignature(
                signature.getDigitalSignature() != null && signature.getDigitalSignature().length > 0);
        response.setStatus(signature.isStatus());
        response.setCreatedDate(signature.getCreatedDate());
        response.setUpdatedDate(signature.getUpdatedDate());

        return response;
    }
}