package com.example.vmo.service;

import com.example.vmo.dto.EmployeeBillingPositionRequest;
import com.example.vmo.dto.EmployeeBillingPositionResponse;

import java.util.List;

public interface EmployeeBillingPositionService {

    /**
     * Create a new employee billing position record
     * 
     * @param request The employee billing position request data
     * @return The created employee billing position response
     */
    EmployeeBillingPositionResponse createEmployeeBillingPosition(EmployeeBillingPositionRequest request);

    /**
     * Update an existing employee billing position record
     * 
     * @param id      The id of the employee billing position record to update
     * @param request The updated employee billing position data
     * @return The updated employee billing position response
     */
    EmployeeBillingPositionResponse updateEmployeeBillingPosition(Long id, EmployeeBillingPositionRequest request);

    /**
     * Get an employee billing position record by id
     * 
     * @param id The id of the employee billing position record to retrieve
     * @return The employee billing position response
     */
    EmployeeBillingPositionResponse getEmployeeBillingPositionById(Long id);

    /**
     * Get all active employee billing position records
     * 
     * @return List of active employee billing position responses
     */
    List<EmployeeBillingPositionResponse> getAllActiveEmployeeBillingPositions();

    /**
     * Get all active employee billing position records for a specific employee
     * 
     * @param employeeId The employee ID to filter by
     * @return List of active employee billing position responses for the given
     *         employee
     */
    List<EmployeeBillingPositionResponse> getEmployeeBillingPositionsByEmployeeId(Long employeeId);

    /**
     * Soft delete an employee billing position record by setting status to false
     * 
     * @param id The id of the employee billing position record to delete
     */
    void deleteEmployeeBillingPosition(Long id);
}