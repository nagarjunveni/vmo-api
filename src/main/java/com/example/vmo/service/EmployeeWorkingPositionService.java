package com.example.vmo.service;

import com.example.vmo.dto.EmployeeWorkingPositionRequest;
import com.example.vmo.dto.EmployeeWorkingPositionResponse;

import java.util.List;

public interface EmployeeWorkingPositionService {

    /**
     * Create a new employee working position record
     * 
     * @param request The employee working position request data
     * @return The created employee working position response
     */
    EmployeeWorkingPositionResponse createEmployeeWorkingPosition(EmployeeWorkingPositionRequest request);

    /**
     * Update an existing employee working position record
     * 
     * @param id      The id of the employee working position record to update
     * @param request The updated employee working position data
     * @return The updated employee working position response
     */
    EmployeeWorkingPositionResponse updateEmployeeWorkingPosition(Long id, EmployeeWorkingPositionRequest request);

    /**
     * Get an employee working position record by id
     * 
     * @param id The id of the employee working position record to retrieve
     * @return The employee working position response
     */
    EmployeeWorkingPositionResponse getEmployeeWorkingPositionById(Long id);

    /**
     * Get all active employee working position records
     * 
     * @return List of active employee working position responses
     */
    List<EmployeeWorkingPositionResponse> getAllActiveEmployeeWorkingPositions();

    /**
     * Get all active employee working position records for a specific employee
     * 
     * @param employeeId The employee ID to filter by
     * @return List of active employee working position responses for the given
     *         employee
     */
    List<EmployeeWorkingPositionResponse> getEmployeeWorkingPositionsByEmployeeId(Long employeeId);

    /**
     * Soft delete an employee working position record by setting status to false
     * 
     * @param id The id of the employee working position record to delete
     */
    void deleteEmployeeWorkingPosition(Long id);
}