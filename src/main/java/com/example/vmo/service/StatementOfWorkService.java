package com.example.vmo.service;

import com.example.vmo.dto.StatementOfWorkRequest;
import com.example.vmo.dto.StatementOfWorkResponse;

import java.util.List;

public interface StatementOfWorkService {

    /**
     * Create a new statement of work with positions
     * 
     * @param request The statement of work request data with positions
     * @return The created statement of work response with positions
     */
    StatementOfWorkResponse createStatementOfWork(StatementOfWorkRequest request);

    /**
     * Update an existing statement of work with positions
     * 
     * @param id      The id of the statement of work to update
     * @param request The updated statement of work data with positions
     * @return The updated statement of work response with positions
     */
    StatementOfWorkResponse updateStatementOfWork(Long id, StatementOfWorkRequest request);

    /**
     * Get a statement of work by id with positions
     * 
     * @param id The id of the statement of work to retrieve
     * @return The statement of work response with positions
     */
    StatementOfWorkResponse getStatementOfWorkById(Long id);

    /**
     * Get a statement of work by custom ID
     * 
     * @param statementOfWorkId The custom ID of the statement of work to retrieve
     * @return The statement of work response
     */
    StatementOfWorkResponse getStatementOfWorkByCustomId(String statementOfWorkId);

    /**
     * Get statements of work by name
     * 
     * @param name The name to search for
     * @return List of statements of work with the specified name
     */
    List<StatementOfWorkResponse> getStatementOfWorksByName(String name);

    /**
     * Get all active statements of work with onsite and offshore counts
     * 
     * @return List of active statement of work responses with position counts
     */
    List<StatementOfWorkResponse> getAllActiveStatementOfWorks();

    /**
     * Soft delete a statement of work by setting status to false
     * 
     * @param id The id of the statement of work to delete
     */
    void deleteStatementOfWork(Long id);

    /**
     * Generate a custom statement of work ID
     * 
     * @param year The year for the ID
     * @return The generated ID in format SOW-YYYY-XXX
     */
    String generateStatementOfWorkId(int year);
}