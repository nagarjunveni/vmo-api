package com.example.vmo.service;

import com.example.vmo.dto.AuthorizedSignatureRequest;
import com.example.vmo.dto.AuthorizedSignatureResponse;

import java.io.IOException;
import java.util.List;

public interface AuthorizedSignatureService {

    /**
     * Create a new authorized signature
     * 
     * @param request The signature request data
     * @return The created signature response
     */
    AuthorizedSignatureResponse createAuthorizedSignature(AuthorizedSignatureRequest request) throws IOException;

    /**
     * Update an existing authorized signature
     * 
     * @param id      The id of the signature to update
     * @param request The updated signature data
     * @return The updated signature response
     */
    AuthorizedSignatureResponse updateAuthorizedSignature(Long id, AuthorizedSignatureRequest request)
            throws IOException;

    /**
     * Get an authorized signature by id
     * 
     * @param id The id of the signature to retrieve
     * @return The signature response
     */
    AuthorizedSignatureResponse getAuthorizedSignatureById(Long id);

    /**
     * Get all active authorized signatures
     * 
     * @return List of active signature responses
     */
    List<AuthorizedSignatureResponse> getAllActiveAuthorizedSignatures();

    /**
     * Soft delete an authorized signature by setting status to false
     * 
     * @param id The id of the signature to delete
     */
    void deleteAuthorizedSignature(Long id);

    /**
     * Get the digital signature file for a specific authorized signature
     * 
     * @param id The id of the signature
     * @return The digital signature as byte array
     */
    byte[] getDigitalSignature(Long id);
}