package com.example.vmo.service.impl;

import com.example.vmo.dto.EmployeeBonusRequest;
import com.example.vmo.dto.EmployeeBonusResponse;
import com.example.vmo.dto.EmployeeResponse;
import com.example.vmo.model.EmployeeBonus;
import com.example.vmo.repository.EmployeeBonusRepository;
import com.example.vmo.repository.EmployeeRepository;
import com.example.vmo.service.EmployeeBonusService;
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
public class EmployeeBonusServiceImpl implements EmployeeBonusService {

    private final EmployeeBonusRepository employeeBonusRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @Override
    @Transactional
    public EmployeeBonusResponse createEmployeeBonus(EmployeeBonusRequest request) {
        validateEmployeeBonusRequest(request);

        EmployeeBonus employeeBonus = new EmployeeBonus();
        updateEmployeeBonusFromRequest(employeeBonus, request);

        // Set created date if not provided
        if (employeeBonus.getCreatedDate() == null) {
            employeeBonus.setCreatedDate(LocalDateTime.now());
        }

        EmployeeBonus savedEmployeeBonus = employeeBonusRepository.save(employeeBonus);
        return mapToEmployeeBonusResponse(savedEmployeeBonus);
    }

    @Override
    @Transactional
    public EmployeeBonusResponse updateEmployeeBonus(Long id, EmployeeBonusRequest request) {
        EmployeeBonus employeeBonus = employeeBonusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee bonus record not found with id: " + id));

        // Validate employee if provided
        if (request.getEmployeeId() != null && !employeeRepository.existsById(request.getEmployeeId())) {
            throw new EntityNotFoundException("Employee not found with id: " + request.getEmployeeId());
        }

        updateEmployeeBonusFromRequest(employeeBonus, request);

        EmployeeBonus updatedEmployeeBonus = employeeBonusRepository.save(employeeBonus);
        return mapToEmployeeBonusResponse(updatedEmployeeBonus);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeBonusResponse getEmployeeBonusById(Long id) {
        EmployeeBonus employeeBonus = employeeBonusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee bonus record not found with id: " + id));

        return mapToEmployeeBonusResponse(employeeBonus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeBonusResponse> getAllActiveEmployeeBonuses() {
        List<EmployeeBonus> employeeBonuses = employeeBonusRepository.findByStatusTrue();
        return employeeBonuses.stream()
                .map(this::mapToEmployeeBonusResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeBonusResponse> getEmployeeBonusesByEmployeeId(Long employeeId) {
        // Validate that the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }

        List<EmployeeBonus> employeeBonuses = employeeBonusRepository.findByEmployeeIdAndStatusTrue(employeeId);
        return employeeBonuses.stream()
                .map(this::mapToEmployeeBonusResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeBonusResponse> getEmployeeBonusHistoryByEmployeeId(Long employeeId) {
        // Validate that the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }

        List<EmployeeBonus> employeeBonuses = employeeBonusRepository
                .findByEmployeeIdAndStatusTrueOrderByEffectiveDateDesc(employeeId);
        return employeeBonuses.stream()
                .map(this::mapToEmployeeBonusResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteEmployeeBonus(Long id) {
        EmployeeBonus employeeBonus = employeeBonusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee bonus record not found with id: " + id));

        employeeBonus.setStatus(false);
        employeeBonusRepository.save(employeeBonus);
    }

    private void validateEmployeeBonusRequest(EmployeeBonusRequest request) {
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

        if (request.getReason() == null || request.getReason().trim().isEmpty()) {
            throw new IllegalArgumentException("Reason is required");
        }

        if (request.getBonusType() == null) {
            throw new IllegalArgumentException("Bonus type is required");
        }
    }

    private void updateEmployeeBonusFromRequest(EmployeeBonus employeeBonus, EmployeeBonusRequest request) {
        if (request.getAmount() != null) {
            employeeBonus.setAmount(request.getAmount());
        }

        if (request.getCreatedDate() != null) {
            employeeBonus.setCreatedDate(request.getCreatedDate());
        }

        if (request.getStatus() != null) {
            employeeBonus.setStatus(request.getStatus());
        }

        if (request.getEmployeeId() != null) {
            employeeBonus.setEmployeeId(request.getEmployeeId());
        }

        if (request.getEffectiveDate() != null) {
            employeeBonus.setEffectiveDate(request.getEffectiveDate());
        }

        if (request.getReason() != null) {
            employeeBonus.setReason(request.getReason());
        }

        if (request.getBonusType() != null) {
            employeeBonus.setBonusType(request.getBonusType());
        }

        if (request.getCreatedBy() != null) {
            employeeBonus.setCreatedBy(request.getCreatedBy());
        }

        if (request.getApprovedBy() != null) {
            employeeBonus.setApprovedBy(request.getApprovedBy());
        }
    }

    private EmployeeBonusResponse mapToEmployeeBonusResponse(EmployeeBonus employeeBonus) {
        EmployeeBonusResponse response = new EmployeeBonusResponse();
        response.setId(employeeBonus.getId());
        response.setAmount(employeeBonus.getAmount());
        response.setCreatedDate(employeeBonus.getCreatedDate());
        response.setStatus(employeeBonus.isStatus());
        response.setEmployeeId(employeeBonus.getEmployeeId());
        response.setEffectiveDate(employeeBonus.getEffectiveDate());
        response.setReason(employeeBonus.getReason());
        response.setBonusType(employeeBonus.getBonusType());
        response.setCreatedBy(employeeBonus.getCreatedBy());
        response.setApprovedBy(employeeBonus.getApprovedBy());
        response.setUpdatedDate(employeeBonus.getUpdatedDate());

        // Get employee information if employeeId is present
        if (employeeBonus.getEmployeeId() != null) {
            try {
                EmployeeResponse employeeResponse = employeeService.getEmployeeById(employeeBonus.getEmployeeId());
                response.setEmployee(employeeResponse);
            } catch (EntityNotFoundException e) {
                // Employee not found, but we still want to return the employee bonus record
            }
        }

        return response;
    }
}