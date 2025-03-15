package com.example.vmo.service;

import com.example.vmo.dto.PositionRequest;
import com.example.vmo.dto.PositionResponse;

import java.util.List;

public interface PositionService {

    /**
     * Create a new position
     * 
     * @param request The position request data
     * @return The created position response
     */
    PositionResponse createPosition(PositionRequest request);

    /**
     * Update an existing position
     * 
     * @param id      The id of the position to update
     * @param request The updated position data
     * @return The updated position response
     */
    PositionResponse updatePosition(Long id, PositionRequest request);

    /**
     * Get a position by id
     * 
     * @param id The id of the position to retrieve
     * @return The position response
     */
    PositionResponse getPositionById(Long id);

    /**
     * Get all active positions
     * 
     * @return List of active position responses
     */
    List<PositionResponse> getAllActivePositions();

    /**
     * Soft delete a position by setting status to false
     * 
     * @param id The id of the position to delete
     */
    void deletePosition(Long id);
}