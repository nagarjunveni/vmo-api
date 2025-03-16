package com.example.vmo.service;

import com.example.vmo.dto.EmployeePayRequest;
import com.example.vmo.dto.EmployeePayResponse;

import java.util.List;

public interface EmployeePayService {

    /**
     * Create a new employee pay record
     * 
     * @param request The employee pay request data
     * @return The created employee pay response
     */
    EmployeePayResponse createEmployeePay(EmployeePayRequest request);

    /**
     * Update an existing employee pay record
     * 
     * @param id      The id of the employee pay record to update
     * @param request The updated employee pay data
     * @return The updated employee pay response
     */
    EmployeePayResponse updateEmployeePay(Long id, EmployeePayRequest request);

    /**
     * Get an employee pay record by id
     * 
     * @param id The id of the employee pay record to retrieve
     * @return The employee pay response
     */
    EmployeePayResponse getEmployeePayById(Long id);

    /**
     * Get all active employee pay records
     * 
     * @return List of active employee pay responses
     */
    List<EmployeePayResponse> getAllActiveEmployeePay();

    /**
     * Get all active employee pay records for a specific employee
     * 
     * @param employeeId The employee ID to filter by
     * @return List of active employee pay responses for the given employee
     */
    List<EmployeePayResponse> getEmployeePayByEmployeeId(Long employeeId);

    /**
     * Get all active employee pay records for a specific employee ordered by
     * effective date descending
     * 
     * @param employeeId The employee ID to filter by
     * @return List of active employee pay responses for the given employee
     */
    List<EmployeePayResponse> getEmployeePayHistoryByEmployeeId(Long employeeId);

    /**
     * Soft delete an employee pay record by setting status to false
     * 
     * @param id The id of the employee pay record to delete
     */
    void deleteEmployeePay(Long id);
}