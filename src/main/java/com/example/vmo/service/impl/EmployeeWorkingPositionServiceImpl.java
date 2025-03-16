package com.example.vmo.service.impl;

import com.example.vmo.dto.EmployeeResponse;
import com.example.vmo.dto.EmployeeWorkingPositionRequest;
import com.example.vmo.dto.EmployeeWorkingPositionResponse;
import com.example.vmo.dto.StatementOfWorkPositionResponse;
import com.example.vmo.model.EmployeeWorkingPosition;
import com.example.vmo.repository.EmployeeRepository;
import com.example.vmo.repository.EmployeeWorkingPositionRepository;
import com.example.vmo.repository.StatementOfWorkPositionRepository;
import com.example.vmo.service.EmployeeService;
import com.example.vmo.service.EmployeeWorkingPositionService;
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
public class EmployeeWorkingPositionServiceImpl implements EmployeeWorkingPositionService {

    private final EmployeeWorkingPositionRepository employeeWorkingPositionRepository;
    private final EmployeeRepository employeeRepository;
    private final StatementOfWorkPositionRepository statementOfWorkPositionRepository;
    private final EmployeeService employeeService;
    private final StatementOfWorkPositionService statementOfWorkPositionService;

    @Override
    @Transactional
    public EmployeeWorkingPositionResponse createEmployeeWorkingPosition(EmployeeWorkingPositionRequest request) {
        validateEmployeeWorkingPositionRequest(request);

        EmployeeWorkingPosition employeeWorkingPosition = new EmployeeWorkingPosition();
        updateEmployeeWorkingPositionFromRequest(employeeWorkingPosition, request);

        // Set assigned date if not provided
        if (employeeWorkingPosition.getAssignedDate() == null) {
            employeeWorkingPosition.setAssignedDate(LocalDateTime.now());
        }

        EmployeeWorkingPosition savedEmployeeWorkingPosition = employeeWorkingPositionRepository
                .save(employeeWorkingPosition);
        return mapToEmployeeWorkingPositionResponse(savedEmployeeWorkingPosition);
    }

    @Override
    @Transactional
    public EmployeeWorkingPositionResponse updateEmployeeWorkingPosition(Long id,
            EmployeeWorkingPositionRequest request) {
        EmployeeWorkingPosition employeeWorkingPosition = employeeWorkingPositionRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee working position record not found with id: " + id));

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

        updateEmployeeWorkingPositionFromRequest(employeeWorkingPosition, request);

        EmployeeWorkingPosition updatedEmployeeWorkingPosition = employeeWorkingPositionRepository
                .save(employeeWorkingPosition);
        return mapToEmployeeWorkingPositionResponse(updatedEmployeeWorkingPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeWorkingPositionResponse getEmployeeWorkingPositionById(Long id) {
        EmployeeWorkingPosition employeeWorkingPosition = employeeWorkingPositionRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee working position record not found with id: " + id));

        return mapToEmployeeWorkingPositionResponse(employeeWorkingPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeWorkingPositionResponse> getAllActiveEmployeeWorkingPositions() {
        List<EmployeeWorkingPosition> employeeWorkingPositions = employeeWorkingPositionRepository.findByStatusTrue();
        return employeeWorkingPositions.stream()
                .map(this::mapToEmployeeWorkingPositionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeWorkingPositionResponse> getEmployeeWorkingPositionsByEmployeeId(Long employeeId) {
        // Validate that the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }

        List<EmployeeWorkingPosition> employeeWorkingPositions = employeeWorkingPositionRepository
                .findByEmployeeIdAndStatusTrue(employeeId);
        return employeeWorkingPositions.stream()
                .map(this::mapToEmployeeWorkingPositionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteEmployeeWorkingPosition(Long id) {
        EmployeeWorkingPosition employeeWorkingPosition = employeeWorkingPositionRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee working position record not found with id: " + id));

        employeeWorkingPosition.setStatus(false);
        employeeWorkingPositionRepository.save(employeeWorkingPosition);
    }

    private void validateEmployeeWorkingPositionRequest(EmployeeWorkingPositionRequest request) {
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

        if (request.getAssignedBy() == null) {
            throw new IllegalArgumentException("Assigned by is required");
        }
    }

    private void updateEmployeeWorkingPositionFromRequest(EmployeeWorkingPosition employeeWorkingPosition,
            EmployeeWorkingPositionRequest request) {
        if (request.getEmployeeId() != null) {
            employeeWorkingPosition.setEmployeeId(request.getEmployeeId());
        }

        if (request.getStatementOfWorkPositionId() != null) {
            employeeWorkingPosition.setStatementOfWorkPositionId(request.getStatementOfWorkPositionId());
        }

        if (request.getStartDate() != null) {
            employeeWorkingPosition.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            employeeWorkingPosition.setEndDate(request.getEndDate());
        }

        if (request.getStatus() != null) {
            employeeWorkingPosition.setStatus(request.getStatus());
        }

        if (request.getAssignedBy() != null) {
            employeeWorkingPosition.setAssignedBy(request.getAssignedBy());
        }

        if (request.getAssignedDate() != null) {
            employeeWorkingPosition.setAssignedDate(request.getAssignedDate());
        }
    }

    private EmployeeWorkingPositionResponse mapToEmployeeWorkingPositionResponse(
            EmployeeWorkingPosition employeeWorkingPosition) {
        EmployeeWorkingPositionResponse response = new EmployeeWorkingPositionResponse();
        response.setId(employeeWorkingPosition.getId());
        response.setEmployeeId(employeeWorkingPosition.getEmployeeId());
        response.setStatementOfWorkPositionId(employeeWorkingPosition.getStatementOfWorkPositionId());
        response.setStartDate(employeeWorkingPosition.getStartDate());
        response.setEndDate(employeeWorkingPosition.getEndDate());
        response.setStatus(employeeWorkingPosition.isStatus());
        response.setAssignedBy(employeeWorkingPosition.getAssignedBy());
        response.setAssignedDate(employeeWorkingPosition.getAssignedDate());
        response.setCreatedDate(employeeWorkingPosition.getCreatedDate());
        response.setUpdatedDate(employeeWorkingPosition.getUpdatedDate());

        // Get employee information if employeeId is present
        if (employeeWorkingPosition.getEmployeeId() != null) {
            try {
                EmployeeResponse employeeResponse = employeeService
                        .getEmployeeById(employeeWorkingPosition.getEmployeeId());
                response.setEmployee(employeeResponse);
            } catch (EntityNotFoundException e) {
                // Employee not found, but we still want to return the employee working position
                // record
            }
        }

        // Get statement of work position information if statementOfWorkPositionId is
        // present
        if (employeeWorkingPosition.getStatementOfWorkPositionId() != null) {
            try {
                StatementOfWorkPositionResponse statementOfWorkPositionResponse = statementOfWorkPositionService
                        .getStatementOfWorkPositionById(employeeWorkingPosition.getStatementOfWorkPositionId());
                response.setStatementOfWorkPosition(statementOfWorkPositionResponse);
            } catch (EntityNotFoundException e) {
                // Statement of work position not found, but we still want to return the
                // employee working position record
            }
        }

        return response;
    }
}