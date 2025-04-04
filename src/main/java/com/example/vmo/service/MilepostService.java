package com.example.vmo.service;

import com.example.vmo.dto.MilepostRequest;
import com.example.vmo.dto.MilepostResponse;

import java.util.List;

public interface MilepostService {

    /**
     * Create a new milepost
     * 
     * @param request The milepost request data
     * @return The created milepost response
     */
    MilepostResponse createMilepost(MilepostRequest request);

    /**
     * Update an existing milepost
     * 
     * @param id      The id of the milepost to update
     * @param request The updated milepost data
     * @return The updated milepost response
     */
    MilepostResponse updateMilepost(Long id, MilepostRequest request);

    /**
     * Get a milepost by id
     * 
     * @param id The id of the milepost to retrieve
     * @return The milepost response
     */
    MilepostResponse getMilepostById(Long id);

    /**
     * Get all active mileposts
     * 
     * @return List of active milepost responses
     */
    List<MilepostResponse> getAllActiveMileposts();

    /**
     * Get all active mileposts for a specific SOW
     * 
     * @param sowId The SOW ID to filter by
     * @return List of active milepost responses for the given SOW
     */
    List<MilepostResponse> getMilepostsBySowId(Long sowId);

    /**
     * Soft delete a milepost by setting status to false
     * 
     * @param id The id of the milepost to delete
     */
    void deleteMilepost(Long id);
}