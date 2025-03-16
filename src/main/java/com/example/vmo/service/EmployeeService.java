package com.example.vmo.service;

import com.example.vmo.dto.EmployeeRequest;
import com.example.vmo.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    /**
     * Create a new employee
     * 
     * @param request The employee request data
     * @return The created employee response
     */
    EmployeeResponse createEmployee(EmployeeRequest request);

    /**
     * Update an existing employee
     * 
     * @param id      The id of the employee to update
     * @param request The updated employee data
     * @return The updated employee response
     */
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    /**
     * Get an employee by id
     * 
     * @param id The id of the employee to retrieve
     * @return The employee response
     */
    EmployeeResponse getEmployeeById(Long id);

    /**
     * Get an employee by email
     * 
     * @param email The email of the employee to retrieve
     * @return The employee response
     */
    EmployeeResponse getEmployeeByEmail(String email);

    /**
     * Get all active employees
     * 
     * @return List of active employee responses
     */
    List<EmployeeResponse> getAllActiveEmployees();

    /**
     * Get all active employees for a vendor
     * 
     * @param vendorId The vendor ID to filter by
     * @return List of active employee responses for the given vendor
     */
    List<EmployeeResponse> getEmployeesByVendorId(Long vendorId);

    /**
     * Soft delete an employee by setting status to false
     * 
     * @param id The id of the employee to delete
     */
    void deleteEmployee(Long id);
}