package com.example.vmo.service;

import com.example.vmo.dto.StatementOfWorkPositionRequest;
import com.example.vmo.dto.StatementOfWorkPositionResponse;

import java.util.List;

public interface StatementOfWorkPositionService {

    /**
     * Create a new StatementOfWorkPosition
     * 
     * @param request The StatementOfWorkPosition request data
     * @return The created StatementOfWorkPosition response
     */
    StatementOfWorkPositionResponse createStatementOfWorkPosition(StatementOfWorkPositionRequest request);

    /**
     * Update an existing StatementOfWorkPosition
     * 
     * @param id      The id of the StatementOfWorkPosition to update
     * @param request The updated StatementOfWorkPosition data
     * @return The updated StatementOfWorkPosition response
     */
    StatementOfWorkPositionResponse updateStatementOfWorkPosition(Long id, StatementOfWorkPositionRequest request);

    /**
     * Get a StatementOfWorkPosition by id
     * 
     * @param id The id of the StatementOfWorkPosition to retrieve
     * @return The StatementOfWorkPosition response
     */
    StatementOfWorkPositionResponse getStatementOfWorkPositionById(Long id);

    /**
     * Get all active StatementOfWorkPositions
     * 
     * @return List of active StatementOfWorkPosition responses
     */
    List<StatementOfWorkPositionResponse> getAllActiveStatementOfWorkPositions();

    /**
     * Get all active StatementOfWorkPositions by SOW ID
     * 
     * @param sowId The statement of work ID
     * @return List of active StatementOfWorkPosition responses for the given SOW
     */
    List<StatementOfWorkPositionResponse> getStatementOfWorkPositionsBySowId(Long sowId);

    /**
     * Soft delete a StatementOfWorkPosition by setting status to false
     * 
     * @param id The id of the StatementOfWorkPosition to delete
     */
    void deleteStatementOfWorkPosition(Long id);
}