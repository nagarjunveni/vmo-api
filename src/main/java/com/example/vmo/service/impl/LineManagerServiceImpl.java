package com.example.vmo.service.impl;

import com.example.vmo.dto.LineManagerRequest;
import com.example.vmo.dto.LineManagerResponse;
import com.example.vmo.model.LineManager;
import com.example.vmo.enums.LineManagerType;
import com.example.vmo.repository.LineManagerRepository;
import com.example.vmo.service.LineManagerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineManagerServiceImpl implements LineManagerService {

    private final LineManagerRepository lineManagerRepository;

    @Override
    @Transactional
    public LineManagerResponse createLineManager(LineManagerRequest request) {
        if (request.getFirstName() == null || request.getLastName() == null ||
                request.getEmail() == null || request.getContactNumber() == null) {
            throw new IllegalArgumentException("User details are required when creating a new user");
        }

        // Validate required fields
        if (request.getType() == null) {
            throw new IllegalArgumentException("Line manager type is required");
        }

        if (request.getDepartment() == null || request.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department is required");
        }

        LineManager lineManager = new LineManager();
        lineManager.setFirstName(request.getFirstName());
        lineManager.setMiddleName(request.getMiddleName());
        lineManager.setLastName(request.getLastName());
        lineManager.setEmail(request.getEmail());
        lineManager.setContactNumber(request.getContactNumber());
        lineManager.setType(request.getType());
        lineManager.setDepartment(request.getDepartment());
        lineManager.setStatus(true);

        LineManager savedLineManager = lineManagerRepository.save(lineManager);
        return mapToResponse(savedLineManager);
    }

    @Override
    @Transactional
    public LineManagerResponse updateLineManager(Long id, LineManagerRequest request) {
        LineManager lineManager = lineManagerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Line Manager not found with id: " + id));

        if (request.getFirstName() == null || request.getLastName() == null ||
                request.getEmail() == null || request.getContactNumber() == null) {
            throw new IllegalArgumentException("User details are required when creating a new user");
        }

        lineManager.setFirstName(request.getFirstName());
        lineManager.setMiddleName(request.getMiddleName());
        lineManager.setLastName(request.getLastName());
        lineManager.setEmail(request.getEmail());
        lineManager.setContactNumber(request.getContactNumber());

        // Update other fields if provided
        if (request.getType() != null) {
            lineManager.setType(request.getType());
        }

        if (request.getDepartment() != null && !request.getDepartment().trim().isEmpty()) {
            lineManager.setDepartment(request.getDepartment());
        }

        LineManager updatedLineManager = lineManagerRepository.save(lineManager);
        return mapToResponse(updatedLineManager);
    }

    @Override
    @Transactional(readOnly = true)
    public LineManagerResponse getLineManagerById(Long id) {
        LineManager lineManager = lineManagerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Line Manager not found with id: " + id));
        return mapToResponse(lineManager);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineManagerResponse> getAllActiveLineManagers() {
        return lineManagerRepository.findByStatusTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineManagerResponse> getLineManagersByType(LineManagerType type) {
        return lineManagerRepository.findByTypeAndStatusTrue(type).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteLineManager(Long id) {
        LineManager lineManager = lineManagerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Line Manager not found with id: " + id));
        lineManager.setStatus(false);
        lineManagerRepository.save(lineManager);
    }

    /**
     * Map entity to response DTO
     */
    private LineManagerResponse mapToResponse(LineManager lineManager) {
        LineManagerResponse response = new LineManagerResponse();
        response.setId(lineManager.getId());

        response.setFirstName(lineManager.getFirstName());
        response.setMiddleName(lineManager.getMiddleName());
        response.setLastName(lineManager.getLastName());
        response.setEmail(lineManager.getEmail());
        response.setContactNumber(lineManager.getContactNumber());
        response.setType(lineManager.getType());
        response.setTypeDisplayName(lineManager.getType().getDisplayName());
        response.setDepartment(lineManager.getDepartment());
        response.setStatus(lineManager.isStatus());
        return response;
    }
}