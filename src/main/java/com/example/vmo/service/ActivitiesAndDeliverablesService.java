package com.example.vmo.service;

import com.example.vmo.dto.ActivitiesAndDeliverablesRequest;
import com.example.vmo.dto.ActivitiesAndDeliverablesResponse;

import java.util.List;

public interface ActivitiesAndDeliverablesService {

    /**
     * Create a new activities and deliverables record
     * 
     * @param request The activities and deliverables request data
     * @return The created activities and deliverables response
     */
    ActivitiesAndDeliverablesResponse createActivitiesAndDeliverables(ActivitiesAndDeliverablesRequest request);

    /**
     * Update an existing activities and deliverables record
     * 
     * @param id      The id of the activities and deliverables record to update
     * @param request The updated activities and deliverables data
     * @return The updated activities and deliverables response
     */
    ActivitiesAndDeliverablesResponse updateActivitiesAndDeliverables(Long id,
            ActivitiesAndDeliverablesRequest request);

    /**
     * Get an activities and deliverables record by id
     * 
     * @param id The id of the activities and deliverables record to retrieve
     * @return The activities and deliverables response
     */
    ActivitiesAndDeliverablesResponse getActivitiesAndDeliverablesById(Long id);

    /**
     * Get all active activities and deliverables records
     * 
     * @return List of active activities and deliverables responses
     */
    List<ActivitiesAndDeliverablesResponse> getAllActiveActivitiesAndDeliverables();

    /**
     * Get all active activities and deliverables records for a specific SOW
     * 
     * @param sowId The SOW ID to filter by
     * @return List of active activities and deliverables responses for the given
     *         SOW
     */
    List<ActivitiesAndDeliverablesResponse> getActivitiesAndDeliverablesBySowId(Long sowId);

    /**
     * Soft delete an activities and deliverables record by setting status to false
     * 
     * @param id The id of the activities and deliverables record to delete
     */
    void deleteActivitiesAndDeliverables(Long id);
}