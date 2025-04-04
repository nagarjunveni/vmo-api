package com.example.vmo.service.impl;

import com.example.vmo.dto.ActivitiesAndDeliverablesRequest;
import com.example.vmo.dto.ActivitiesAndDeliverablesResponse;
import com.example.vmo.model.ActivitiesAndDeliverables;
import com.example.vmo.repository.ActivitiesAndDeliverablesRepository;
import com.example.vmo.repository.StatementOfWorkRepository;
import com.example.vmo.service.ActivitiesAndDeliverablesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivitiesAndDeliverablesServiceImpl implements ActivitiesAndDeliverablesService {

    private final ActivitiesAndDeliverablesRepository activitiesAndDeliverablesRepository;
    private final StatementOfWorkRepository statementOfWorkRepository;

    @Override
    @Transactional
    public ActivitiesAndDeliverablesResponse createActivitiesAndDeliverables(ActivitiesAndDeliverablesRequest request) {
        validateActivitiesAndDeliverablesRequest(request);

        ActivitiesAndDeliverables activitiesAndDeliverables = new ActivitiesAndDeliverables();
        updateActivitiesAndDeliverablesFromRequest(activitiesAndDeliverables, request);

        ActivitiesAndDeliverables savedActivitiesAndDeliverables = activitiesAndDeliverablesRepository
                .save(activitiesAndDeliverables);
        return mapToActivitiesAndDeliverablesResponse(savedActivitiesAndDeliverables);
    }

    @Override
    @Transactional
    public ActivitiesAndDeliverablesResponse updateActivitiesAndDeliverables(Long id,
            ActivitiesAndDeliverablesRequest request) {
        ActivitiesAndDeliverables activitiesAndDeliverables = activitiesAndDeliverablesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Activities and deliverables record not found with id: " + id));

        // Validate SOW if provided
        if (request.getSowId() != null && !statementOfWorkRepository.existsById(request.getSowId())) {
            throw new EntityNotFoundException("Statement of work not found with id: " + request.getSowId());
        }

        updateActivitiesAndDeliverablesFromRequest(activitiesAndDeliverables, request);

        ActivitiesAndDeliverables updatedActivitiesAndDeliverables = activitiesAndDeliverablesRepository
                .save(activitiesAndDeliverables);
        return mapToActivitiesAndDeliverablesResponse(updatedActivitiesAndDeliverables);
    }

    @Override
    @Transactional(readOnly = true)
    public ActivitiesAndDeliverablesResponse getActivitiesAndDeliverablesById(Long id) {
        ActivitiesAndDeliverables activitiesAndDeliverables = activitiesAndDeliverablesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Activities and deliverables record not found with id: " + id));

        return mapToActivitiesAndDeliverablesResponse(activitiesAndDeliverables);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivitiesAndDeliverablesResponse> getAllActiveActivitiesAndDeliverables() {
        List<ActivitiesAndDeliverables> activitiesAndDeliverablesList = activitiesAndDeliverablesRepository
                .findByStatusTrue();
        return activitiesAndDeliverablesList.stream()
                .map(this::mapToActivitiesAndDeliverablesResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivitiesAndDeliverablesResponse> getActivitiesAndDeliverablesBySowId(Long sowId) {
        // Validate that the SOW exists
        if (!statementOfWorkRepository.existsById(sowId)) {
            throw new EntityNotFoundException("Statement of work not found with id: " + sowId);
        }

        List<ActivitiesAndDeliverables> activitiesAndDeliverablesList = activitiesAndDeliverablesRepository
                .findBySowIdAndStatusTrue(sowId);
        return activitiesAndDeliverablesList.stream()
                .map(this::mapToActivitiesAndDeliverablesResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteActivitiesAndDeliverables(Long id) {
        ActivitiesAndDeliverables activitiesAndDeliverables = activitiesAndDeliverablesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Activities and deliverables record not found with id: " + id));

        activitiesAndDeliverables.setStatus(false);
        activitiesAndDeliverablesRepository.save(activitiesAndDeliverables);
    }

    private void validateActivitiesAndDeliverablesRequest(ActivitiesAndDeliverablesRequest request) {
        if (request.getPhase() == null || request.getPhase().trim().isEmpty()) {
            throw new IllegalArgumentException("Phase is required");
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name is required");
        }

        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }

        if (request.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required");
        }

        if (request.getSowId() == null) {
            throw new IllegalArgumentException("SOW ID is required");
        }

        if (!statementOfWorkRepository.existsById(request.getSowId())) {
            throw new EntityNotFoundException("Statement of work not found with id: " + request.getSowId());
        }
    }

    private void updateActivitiesAndDeliverablesFromRequest(ActivitiesAndDeliverables activitiesAndDeliverables,
            ActivitiesAndDeliverablesRequest request) {
        if (request.getPhase() != null) {
            activitiesAndDeliverables.setPhase(request.getPhase());
        }

        if (request.getName() != null) {
            activitiesAndDeliverables.setName(request.getName());
        }

        if (request.getDeliverable() != null) {
            activitiesAndDeliverables.setDeliverable(request.getDeliverable());
        }

        if (request.getStartDate() != null) {
            activitiesAndDeliverables.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            activitiesAndDeliverables.setEndDate(request.getEndDate());
        }

        if (request.getSowId() != null) {
            activitiesAndDeliverables.setSowId(request.getSowId());
        }
    }

    private ActivitiesAndDeliverablesResponse mapToActivitiesAndDeliverablesResponse(
            ActivitiesAndDeliverables activitiesAndDeliverables) {
        ActivitiesAndDeliverablesResponse response = new ActivitiesAndDeliverablesResponse();
        response.setId(activitiesAndDeliverables.getId());
        response.setPhase(activitiesAndDeliverables.getPhase());
        response.setName(activitiesAndDeliverables.getName());
        response.setDeliverable(activitiesAndDeliverables.getDeliverable());
        response.setStartDate(activitiesAndDeliverables.getStartDate());
        response.setEndDate(activitiesAndDeliverables.getEndDate());
        response.setSowId(activitiesAndDeliverables.getSowId());
        response.setStatus(activitiesAndDeliverables.isStatus());
        response.setCreatedDate(activitiesAndDeliverables.getCreatedDate());
        response.setUpdatedDate(activitiesAndDeliverables.getUpdatedDate());

        return response;
    }
}