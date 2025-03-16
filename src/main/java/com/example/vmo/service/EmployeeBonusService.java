package com.example.vmo.service;

import com.example.vmo.dto.EmployeeBonusRequest;
import com.example.vmo.dto.EmployeeBonusResponse;

import java.util.List;

public interface EmployeeBonusService {

    /**
     * Create a new employee bonus record
     * 
     * @param request The employee bonus request data
     * @return The created employee bonus response
     */
    EmployeeBonusResponse createEmployeeBonus(EmployeeBonusRequest request);

    /**
     * Update an existing employee bonus record
     * 
     * @param id      The id of the employee bonus record to update
     * @param request The updated employee bonus data
     * @return The updated employee bonus response
     */
    EmployeeBonusResponse updateEmployeeBonus(Long id, EmployeeBonusRequest request);

    /**
     * Get an employee bonus record by id
     * 
     * @param id The id of the employee bonus record to retrieve
     * @return The employee bonus response
     */
    EmployeeBonusResponse getEmployeeBonusById(Long id);

    /**
     * Get all active employee bonus records
     * 
     * @return List of active employee bonus responses
     */
    List<EmployeeBonusResponse> getAllActiveEmployeeBonuses();

    /**
     * Get all active employee bonus records for a specific employee
     * 
     * @param employeeId The employee ID to filter by
     * @return List of active employee bonus responses for the given employee
     */
    List<EmployeeBonusResponse> getEmployeeBonusesByEmployeeId(Long employeeId);

    /**
     * Get all active employee bonus records for a specific employee ordered by
     * effective date descending
     * 
     * @param employeeId The employee ID to filter by
     * @return List of active employee bonus responses for the given employee
     */
    List<EmployeeBonusResponse> getEmployeeBonusHistoryByEmployeeId(Long employeeId);

    /**
     * Soft delete an employee bonus record by setting status to false
     * 
     * @param id The id of the employee bonus record to delete
     */
    void deleteEmployeeBonus(Long id);
}