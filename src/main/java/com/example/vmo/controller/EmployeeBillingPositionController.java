package com.example.vmo.controller;

import com.example.vmo.dto.EmployeeBillingPositionRequest;
import com.example.vmo.dto.EmployeeBillingPositionResponse;
import com.example.vmo.service.EmployeeBillingPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-billing-position")
@RequiredArgsConstructor
public class EmployeeBillingPositionController {

    private final EmployeeBillingPositionService employeeBillingPositionService;

    /**
     * Create a new employee billing position record
     */
    @PostMapping
    public ResponseEntity<EmployeeBillingPositionResponse> createEmployeeBillingPosition(
            @RequestBody EmployeeBillingPositionRequest request) {
        EmployeeBillingPositionResponse response = employeeBillingPositionService
                .createEmployeeBillingPosition(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing employee billing position record
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeBillingPositionResponse> updateEmployeeBillingPosition(
            @PathVariable Long id,
            @RequestBody EmployeeBillingPositionRequest request) {
        EmployeeBillingPositionResponse response = employeeBillingPositionService.updateEmployeeBillingPosition(id,
                request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get an employee billing position record by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeBillingPositionResponse> getEmployeeBillingPositionById(@PathVariable Long id) {
        EmployeeBillingPositionResponse response = employeeBillingPositionService.getEmployeeBillingPositionById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active employee billing position records
     */
    @GetMapping
    public ResponseEntity<List<EmployeeBillingPositionResponse>> getAllActiveEmployeeBillingPositions() {
        List<EmployeeBillingPositionResponse> responses = employeeBillingPositionService
                .getAllActiveEmployeeBillingPositions();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get all active employee billing position records for a specific employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeBillingPositionResponse>> getEmployeeBillingPositionsByEmployeeId(
            @PathVariable Long employeeId) {
        List<EmployeeBillingPositionResponse> responses = employeeBillingPositionService
                .getEmployeeBillingPositionsByEmployeeId(employeeId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete an employee billing position record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeBillingPosition(@PathVariable Long id) {
        employeeBillingPositionService.deleteEmployeeBillingPosition(id);
        return ResponseEntity.noContent().build();
    }
}