package com.example.vmo.service.impl;

import com.example.vmo.dto.EmployeePayRequest;
import com.example.vmo.dto.EmployeePayResponse;
import com.example.vmo.dto.EmployeeResponse;
import com.example.vmo.enums.PayType;
import com.example.vmo.model.EmployeePay;
import com.example.vmo.repository.EmployeePayRepository;
import com.example.vmo.repository.EmployeeRepository;
import com.example.vmo.service.EmployeePayService;
import com.example.vmo.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeePayServiceImpl implements EmployeePayService {

    private final EmployeePayRepository employeePayRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @Override
    @Transactional
    public EmployeePayResponse createEmployeePay(EmployeePayRequest request) {
        validateEmployeePayRequest(request);

        EmployeePay employeePay = new EmployeePay();
        updateEmployeePayFromRequest(employeePay, request);

        // Set created date if not provided
        if (employeePay.getCreatedDate() == null) {
            employeePay.setCreatedDate(LocalDateTime.now());
        }

        EmployeePay savedEmployeePay = employeePayRepository.save(employeePay);
        return mapToEmployeePayResponse(savedEmployeePay);
    }

    @Override
    @Transactional
    public EmployeePayResponse updateEmployeePay(Long id, EmployeePayRequest request) {
        EmployeePay employeePay = employeePayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee pay record not found with id: " + id));

        // Validate if type is provided and requires specific rates
        if (request.getType() != null) {
            validatePayTypeRequirements(request.getType(), request.getHourlyRate(), request.getMonthlyRate());
        }

        // Validate employee if provided
        if (request.getEmployeeId() != null && !employeeRepository.existsById(request.getEmployeeId())) {
            throw new EntityNotFoundException("Employee not found with id: " + request.getEmployeeId());
        }

        updateEmployeePayFromRequest(employeePay, request);

        EmployeePay updatedEmployeePay = employeePayRepository.save(employeePay);
        return mapToEmployeePayResponse(updatedEmployeePay);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeePayResponse getEmployeePayById(Long id) {
        EmployeePay employeePay = employeePayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee pay record not found with id: " + id));

        return mapToEmployeePayResponse(employeePay);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeePayResponse> getAllActiveEmployeePay() {
        List<EmployeePay> employeePays = employeePayRepository.findByStatusTrue();
        return employeePays.stream()
                .map(this::mapToEmployeePayResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeePayResponse> getEmployeePayByEmployeeId(Long employeeId) {
        // Validate that the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }

        List<EmployeePay> employeePays = employeePayRepository.findByEmployeeIdAndStatusTrue(employeeId);
        return employeePays.stream()
                .map(this::mapToEmployeePayResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeePayResponse> getEmployeePayHistoryByEmployeeId(Long employeeId) {
        // Validate that the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }

        List<EmployeePay> employeePays = employeePayRepository
                .findByEmployeeIdAndStatusTrueOrderByEffectiveDateDesc(employeeId);
        return employeePays.stream()
                .map(this::mapToEmployeePayResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteEmployeePay(Long id) {
        EmployeePay employeePay = employeePayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee pay record not found with id: " + id));

        employeePay.setStatus(false);
        employeePayRepository.save(employeePay);
    }

    private void validateEmployeePayRequest(EmployeePayRequest request) {
        if (request.getType() == null) {
            throw new IllegalArgumentException("Pay type is required");
        }

        if (request.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }

        if (request.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }

        if (!employeeRepository.existsById(request.getEmployeeId())) {
            throw new EntityNotFoundException("Employee not found with id: " + request.getEmployeeId());
        }

        if (request.getEffectiveDate() == null) {
            throw new IllegalArgumentException("Effective date is required");
        }

        validatePayTypeRequirements(request.getType(), request.getHourlyRate(), request.getMonthlyRate());
    }

    private void validatePayTypeRequirements(PayType type, Object hourlyRate, Object monthlyRate) {
        if (type == PayType.Hourly && hourlyRate == null) {
            throw new IllegalArgumentException("Hourly rate is required for Hourly pay type");
        }

        if (type == PayType.Monthly && monthlyRate == null) {
            throw new IllegalArgumentException("Monthly rate is required for Monthly pay type");
        }
    }

    private void updateEmployeePayFromRequest(EmployeePay employeePay, EmployeePayRequest request) {
        if (request.getType() != null) {
            employeePay.setType(request.getType());
        }

        if (request.getAmount() != null) {
            employeePay.setAmount(request.getAmount());
        }

        if (request.getCreatedDate() != null) {
            employeePay.setCreatedDate(request.getCreatedDate());
        }

        if (request.getStatus() != null) {
            employeePay.setStatus(request.getStatus());
        }

        if (request.getEmployeeId() != null) {
            employeePay.setEmployeeId(request.getEmployeeId());
        }

        if (request.getHourlyRate() != null) {
            employeePay.setHourlyRate(request.getHourlyRate());
        }

        if (request.getMonthlyRate() != null) {
            employeePay.setMonthlyRate(request.getMonthlyRate());
        }

        if (request.getEffectiveDate() != null) {
            employeePay.setEffectiveDate(request.getEffectiveDate());
        }
    }

    private EmployeePayResponse mapToEmployeePayResponse(EmployeePay employeePay) {
        EmployeePayResponse response = new EmployeePayResponse();
        response.setId(employeePay.getId());
        response.setType(employeePay.getType());
        response.setAmount(employeePay.getAmount());
        response.setCreatedDate(employeePay.getCreatedDate());
        response.setStatus(employeePay.isStatus());
        response.setEmployeeId(employeePay.getEmployeeId());
        response.setHourlyRate(employeePay.getHourlyRate());
        response.setMonthlyRate(employeePay.getMonthlyRate());
        response.setEffectiveDate(employeePay.getEffectiveDate());
        response.setUpdatedDate(employeePay.getUpdatedDate());

        // Get employee information if employeeId is present
        if (employeePay.getEmployeeId() != null) {
            try {
                EmployeeResponse employeeResponse = employeeService.getEmployeeById(employeePay.getEmployeeId());
                response.setEmployee(employeeResponse);
            } catch (EntityNotFoundException e) {
                // Employee not found, but we still want to return the employee pay record
            }
        }

        return response;
    }
}