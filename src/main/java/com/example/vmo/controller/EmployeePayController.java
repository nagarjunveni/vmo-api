package com.example.vmo.controller;

import com.example.vmo.dto.EmployeePayRequest;
import com.example.vmo.dto.EmployeePayResponse;
import com.example.vmo.service.EmployeePayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-pay")
@RequiredArgsConstructor
public class EmployeePayController {

    private final EmployeePayService employeePayService;

    /**
     * Create a new employee pay record
     */
    @PostMapping
    public ResponseEntity<EmployeePayResponse> createEmployeePay(@RequestBody EmployeePayRequest request) {
        EmployeePayResponse response = employeePayService.createEmployeePay(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing employee pay record
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeePayResponse> updateEmployeePay(
            @PathVariable Long id,
            @RequestBody EmployeePayRequest request) {
        EmployeePayResponse response = employeePayService.updateEmployeePay(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get an employee pay record by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeePayResponse> getEmployeePayById(@PathVariable Long id) {
        EmployeePayResponse response = employeePayService.getEmployeePayById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active employee pay records
     */
    @GetMapping
    public ResponseEntity<List<EmployeePayResponse>> getAllActiveEmployeePay() {
        List<EmployeePayResponse> responses = employeePayService.getAllActiveEmployeePay();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get all active employee pay records for a specific employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeePayResponse>> getEmployeePayByEmployeeId(@PathVariable Long employeeId) {
        List<EmployeePayResponse> responses = employeePayService.getEmployeePayByEmployeeId(employeeId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Get employee pay history for a specific employee
     */
    @GetMapping("/employee/{employeeId}/history")
    public ResponseEntity<List<EmployeePayResponse>> getEmployeePayHistoryByEmployeeId(@PathVariable Long employeeId) {
        List<EmployeePayResponse> responses = employeePayService.getEmployeePayHistoryByEmployeeId(employeeId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete an employee pay record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeePay(@PathVariable Long id) {
        employeePayService.deleteEmployeePay(id);
        return ResponseEntity.noContent().build();
    }
}