package com.example.vmo.service;

import com.example.vmo.dto.LineManagerRequest;
import com.example.vmo.dto.LineManagerResponse;
import com.example.vmo.enums.LineManagerType;

import java.util.List;

public interface LineManagerService {

    /**
     * Create a new line manager
     * 
     * @param request The line manager request data
     * @return The created line manager response
     */
    LineManagerResponse createLineManager(LineManagerRequest request);

    /**
     * Update an existing line manager
     * 
     * @param id      The id of the line manager to update
     * @param request The updated line manager data
     * @return The updated line manager response
     */
    LineManagerResponse updateLineManager(Long id, LineManagerRequest request);

    /**
     * Get a line manager by id
     * 
     * @param id The id of the line manager to retrieve
     * @return The line manager response
     */
    LineManagerResponse getLineManagerById(Long id);

    /**
     * Get all active line managers
     * 
     * @return List of active line manager responses
     */
    List<LineManagerResponse> getAllActiveLineManagers();

    /**
     * Get line managers by type
     * 
     * @param type The type to search for
     * @return List of line managers with the specified type
     */
    List<LineManagerResponse> getLineManagersByType(LineManagerType type);

    /**
     * Soft delete a line manager by setting status to false
     * 
     * @param id The id of the line manager to delete
     */
    void deleteLineManager(Long id);
}