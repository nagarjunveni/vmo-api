package com.example.vmo.controller;

import com.example.vmo.dto.EmployeeBonusRequest;
import com.example.vmo.dto.EmployeeBonusResponse;
import com.example.vmo.service.EmployeeBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-bonus")
@RequiredArgsConstructor
public class EmployeeBonusController {

    private final EmployeeBonusService employeeBonusService;

    /**
     * Create a new employee bonus record
     */
    @PostMapping
    public ResponseEntity<EmployeeBonusResponse> createEmployeeBonus(@RequestBody EmployeeBonusRequest request) {
        EmployeeBonusResponse response = employeeBonusService.createEmployeeBonus(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing employee bonus record
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeBonusResponse> updateEmployeeBonus(
            @PathVariable Long id,
            @RequestBody EmployeeBonusRequest request) {
        EmployeeBonusResponse response = employeeBonusService.updateEmployeeBonus(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get an employee bonus record by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeBonusResponse> getEmployeeBonusById(@PathVariable Long id) {
        EmployeeBonusResponse response = employeeBonusService.getEmployeeBonusById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active employee bonus records
     */
    @GetMapping
    public ResponseEntity<List<EmployeeBonusResponse>> getAllActiveEmployeeBonuses() {
        List<EmployeeBonusResponse> responses = employeeBonusService.getAllActiveEmployeeBonuses();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get all active employee bonus records for a specific employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeBonusResponse>> getEmployeeBonusesByEmployeeId(@PathVariable Long employeeId) {
        List<EmployeeBonusResponse> responses = employeeBonusService.getEmployeeBonusesByEmployeeId(employeeId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Get employee bonus history for a specific employee
     */
    @GetMapping("/employee/{employeeId}/history")
    public ResponseEntity<List<EmployeeBonusResponse>> getEmployeeBonusHistoryByEmployeeId(
            @PathVariable Long employeeId) {
        List<EmployeeBonusResponse> responses = employeeBonusService.getEmployeeBonusHistoryByEmployeeId(employeeId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete an employee bonus record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeBonus(@PathVariable Long id) {
        employeeBonusService.deleteEmployeeBonus(id);
        return ResponseEntity.noContent().build();
    }
}