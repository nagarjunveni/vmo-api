package com.example.vmo.service.impl;

import com.example.vmo.dto.EmployeeBillingPositionRequest;
import com.example.vmo.dto.EmployeeBillingPositionResponse;
import com.example.vmo.dto.EmployeeResponse;
import com.example.vmo.dto.StatementOfWorkPositionResponse;
import com.example.vmo.model.EmployeeBillingPosition;
import com.example.vmo.repository.EmployeeBillingPositionRepository;
import com.example.vmo.repository.EmployeeRepository;
import com.example.vmo.repository.StatementOfWorkPositionRepository;
import com.example.vmo.service.EmployeeBillingPositionService;
import com.example.vmo.service.EmployeeService;
import com.example.vmo.service.StatementOfWorkPositionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeBillingPositionServiceImpl implements EmployeeBillingPositionService {

    private final EmployeeBillingPositionRepository employeeBillingPositionRepository;
    private final EmployeeRepository employeeRepository;
    private final StatementOfWorkPositionRepository statementOfWorkPositionRepository;
    private final EmployeeService employeeService;
    private final StatementOfWorkPositionService statementOfWorkPositionService;

    @Override
    @Transactional
    public EmployeeBillingPositionResponse createEmployeeBillingPosition(EmployeeBillingPositionRequest request) {
        validateEmployeeBillingPositionRequest(request);

        EmployeeBillingPosition employeeBillingPosition = new EmployeeBillingPosition();
        updateEmployeeBillingPositionFromRequest(employeeBillingPosition, request);

        // Set billed date if not provided
        if (employeeBillingPosition.getBilledDate() == null) {
            employeeBillingPosition.setBilledDate(LocalDateTime.now());
        }

        EmployeeBillingPosition savedEmployeeBillingPosition = employeeBillingPositionRepository
                .save(employeeBillingPosition);
        return mapToEmployeeBillingPositionResponse(savedEmployeeBillingPosition);
    }

    @Override
    @Transactional
    public EmployeeBillingPositionResponse updateEmployeeBillingPosition(Long id,
            EmployeeBillingPositionRequest request) {
        EmployeeBillingPosition employeeBillingPosition = employeeBillingPositionRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee billing position record not found with id: " + id));

        // Validate employee if provided
        if (request.getEmployeeId() != null && !employeeRepository.existsById(request.getEmployeeId())) {
            throw new EntityNotFoundException("Employee not found with id: " + request.getEmployeeId());
        }

        // Validate statement of work position if provided
        if (request.getStatementOfWorkPositionId() != null &&
                !statementOfWorkPositionRepository.existsById(request.getStatementOfWorkPositionId())) {
            throw new EntityNotFoundException(
                    "Statement of work position not found with id: " + request.getStatementOfWorkPositionId());
        }

        updateEmployeeBillingPositionFromRequest(employeeBillingPosition, request);

        EmployeeBillingPosition updatedEmployeeBillingPosition = employeeBillingPositionRepository
                .save(employeeBillingPosition);
        return mapToEmployeeBillingPositionResponse(updatedEmployeeBillingPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeBillingPositionResponse getEmployeeBillingPositionById(Long id) {
        EmployeeBillingPosition employeeBillingPosition = employeeBillingPositionRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee billing position record not found with id: " + id));

        return mapToEmployeeBillingPositionResponse(employeeBillingPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeBillingPositionResponse> getAllActiveEmployeeBillingPositions() {
        List<EmployeeBillingPosition> employeeBillingPositions = employeeBillingPositionRepository.findByStatusTrue();
        return employeeBillingPositions.stream()
                .map(this::mapToEmployeeBillingPositionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeBillingPositionResponse> getEmployeeBillingPositionsByEmployeeId(Long employeeId) {
        // Validate that the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }

        List<EmployeeBillingPosition> employeeBillingPositions = employeeBillingPositionRepository
                .findByEmployeeIdAndStatusTrue(employeeId);
        return employeeBillingPositions.stream()
                .map(this::mapToEmployeeBillingPositionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteEmployeeBillingPosition(Long id) {
        EmployeeBillingPosition employeeBillingPosition = employeeBillingPositionRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee billing position record not found with id: " + id));

        employeeBillingPosition.setStatus(false);
        employeeBillingPositionRepository.save(employeeBillingPosition);
    }

    private void validateEmployeeBillingPositionRequest(EmployeeBillingPositionRequest request) {
        if (request.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }

        if (!employeeRepository.existsById(request.getEmployeeId())) {
            throw new EntityNotFoundException("Employee not found with id: " + request.getEmployeeId());
        }

        if (request.getStatementOfWorkPositionId() == null) {
            throw new IllegalArgumentException("Statement of work position ID is required");
        }

        if (!statementOfWorkPositionRepository.existsById(request.getStatementOfWorkPositionId())) {
            throw new EntityNotFoundException(
                    "Statement of work position not found with id: " + request.getStatementOfWorkPositionId());
        }

        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }

        if (request.getBilledBy() == null) {
            throw new IllegalArgumentException("Billed by is required");
        }
    }

    private void updateEmployeeBillingPositionFromRequest(EmployeeBillingPosition employeeBillingPosition,
            EmployeeBillingPositionRequest request) {
        if (request.getEmployeeId() != null) {
            employeeBillingPosition.setEmployeeId(request.getEmployeeId());
        }

        if (request.getStatementOfWorkPositionId() != null) {
            employeeBillingPosition.setStatementOfWorkPositionId(request.getStatementOfWorkPositionId());
        }

        if (request.getStartDate() != null) {
            employeeBillingPosition.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            employeeBillingPosition.setEndDate(request.getEndDate());
        }

        if (request.getStatus() != null) {
            employeeBillingPosition.setStatus(request.getStatus());
        }

        if (request.getBilledBy() != null) {
            employeeBillingPosition.setBilledBy(request.getBilledBy());
        }

        if (request.getBilledDate() != null) {
            employeeBillingPosition.setBilledDate(request.getBilledDate());
        }
    }

    private EmployeeBillingPositionResponse mapToEmployeeBillingPositionResponse(
            EmployeeBillingPosition employeeBillingPosition) {
        EmployeeBillingPositionResponse response = new EmployeeBillingPositionResponse();
        response.setId(employeeBillingPosition.getId());
        response.setEmployeeId(employeeBillingPosition.getEmployeeId());
        response.setStatementOfWorkPositionId(employeeBillingPosition.getStatementOfWorkPositionId());
        response.setStartDate(employeeBillingPosition.getStartDate());
        response.setEndDate(employeeBillingPosition.getEndDate());
        response.setStatus(employeeBillingPosition.isStatus());
        response.setBilledBy(employeeBillingPosition.getBilledBy());
        response.setBilledDate(employeeBillingPosition.getBilledDate());
        response.setCreatedDate(employeeBillingPosition.getCreatedDate());
        response.setUpdatedDate(employeeBillingPosition.getUpdatedDate());

        // Get employee information if employeeId is present
        if (employeeBillingPosition.getEmployeeId() != null) {
            try {
                EmployeeResponse employeeResponse = employeeService
                        .getEmployeeById(employeeBillingPosition.getEmployeeId());
                response.setEmployee(employeeResponse);
            } catch (EntityNotFoundException e) {
                // Employee not found, but we still want to return the employee billing position
                // record
            }
        }

        // Get statement of work position information if statementOfWorkPositionId is
        // present
        if (employeeBillingPosition.getStatementOfWorkPositionId() != null) {
            try {
                StatementOfWorkPositionResponse statementOfWorkPositionResponse = statementOfWorkPositionService
                        .getStatementOfWorkPositionById(employeeBillingPosition.getStatementOfWorkPositionId());
                response.setStatementOfWorkPosition(statementOfWorkPositionResponse);
            } catch (EntityNotFoundException e) {
                // Statement of work position not found, but we still want to return the
                // employee billing position record
            }
        }

        return response;
    }
}