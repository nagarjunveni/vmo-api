package com.example.vmo.controller;

import com.example.vmo.dto.EmployeeWorkingPositionRequest;
import com.example.vmo.dto.EmployeeWorkingPositionResponse;
import com.example.vmo.service.EmployeeWorkingPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-working-position")
@RequiredArgsConstructor
public class EmployeeWorkingPositionController {

    private final EmployeeWorkingPositionService employeeWorkingPositionService;

    /**
     * Create a new employee working position record
     */
    @PostMapping
    public ResponseEntity<EmployeeWorkingPositionResponse> createEmployeeWorkingPosition(
            @RequestBody EmployeeWorkingPositionRequest request) {
        EmployeeWorkingPositionResponse response = employeeWorkingPositionService
                .createEmployeeWorkingPosition(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing employee working position record
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeWorkingPositionResponse> updateEmployeeWorkingPosition(
            @PathVariable Long id,
            @RequestBody EmployeeWorkingPositionRequest request) {
        EmployeeWorkingPositionResponse response = employeeWorkingPositionService.updateEmployeeWorkingPosition(id,
                request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get an employee working position record by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeWorkingPositionResponse> getEmployeeWorkingPositionById(@PathVariable Long id) {
        EmployeeWorkingPositionResponse response = employeeWorkingPositionService.getEmployeeWorkingPositionById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active employee working position records
     */
    @GetMapping
    public ResponseEntity<List<EmployeeWorkingPositionResponse>> getAllActiveEmployeeWorkingPositions() {
        List<EmployeeWorkingPositionResponse> responses = employeeWorkingPositionService
                .getAllActiveEmployeeWorkingPositions();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get all active employee working position records for a specific employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeWorkingPositionResponse>> getEmployeeWorkingPositionsByEmployeeId(
            @PathVariable Long employeeId) {
        List<EmployeeWorkingPositionResponse> responses = employeeWorkingPositionService
                .getEmployeeWorkingPositionsByEmployeeId(employeeId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete an employee working position record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeWorkingPosition(@PathVariable Long id) {
        employeeWorkingPositionService.deleteEmployeeWorkingPosition(id);
        return ResponseEntity.noContent().build();
    }
}