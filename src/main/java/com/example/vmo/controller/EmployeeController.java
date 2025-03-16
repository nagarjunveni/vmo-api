package com.example.vmo.controller;

import com.example.vmo.dto.EmployeeRequest;
import com.example.vmo.dto.EmployeeResponse;
import com.example.vmo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Create a new employee
     */
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.createEmployee(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing employee
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get an employee by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get an employee by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeResponse> getEmployeeByEmail(@PathVariable String email) {
        EmployeeResponse response = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllActiveEmployees() {
        List<EmployeeResponse> responses = employeeService.getAllActiveEmployees();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get employees by vendor ID
     */
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByVendorId(@PathVariable Long vendorId) {
        List<EmployeeResponse> responses = employeeService.getEmployeesByVendorId(vendorId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete an employee
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}