package com.example.vmo.service.impl;

import com.example.vmo.dto.AuthorizedSignatureResponse;
import com.example.vmo.dto.LineManagerResponse;
import com.example.vmo.dto.StatementOfWorkRequest;
import com.example.vmo.dto.StatementOfWorkResponse;
import com.example.vmo.model.AuthorizedSignature;
import com.example.vmo.model.LineManager;
import com.example.vmo.model.StatementOfWork;
import com.example.vmo.repository.AuthorizedSignatureRepository;
import com.example.vmo.repository.LineManagerRepository;
import com.example.vmo.repository.StatementOfWorkRepository;
import com.example.vmo.service.AuthorizedSignatureService;
import com.example.vmo.service.LineManagerService;
import com.example.vmo.service.StatementOfWorkService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatementOfWorkServiceImpl implements StatementOfWorkService {

    private final StatementOfWorkRepository statementOfWorkRepository;
    private final LineManagerRepository lineManagerRepository;
    private final AuthorizedSignatureRepository authorizedSignatureRepository;
    private final LineManagerService lineManagerService;
    private final AuthorizedSignatureService authorizedSignatureService;

    @Override
    @Transactional
    public StatementOfWorkResponse createStatementOfWork(StatementOfWorkRequest request) {
        // Validate required fields
        validateRequest(request);

        // Get related entities
        LineManager lineManager = getLineManager(request.getLineManagerId());
        LineManager escalationManager = getLineManager(request.getEscalationManagerId());
        LineManager vendorEscalationManager = getLineManager(request.getVendorEscalationManagerId());
        AuthorizedSignature authorizedSignature = getAuthorizedSignature(request.getAuthorizedSignatureId());

        // Create new statement of work
        StatementOfWork statementOfWork = new StatementOfWork();
        statementOfWork.setStatementOfWorkId(generateStatementOfWorkId(request.getStartDate().getYear()));
        statementOfWork.setName(request.getName());
        statementOfWork.setDescription(request.getDescription());
        statementOfWork.setStartDate(request.getStartDate());
        statementOfWork.setEndDate(request.getEndDate());
        statementOfWork.setType(request.getType());
        statementOfWork.setFixedBidAmount(request.getFixedBidAmount());
        statementOfWork.setProjectState(request.getProjectState());
        statementOfWork.setLineManager(lineManager);
        statementOfWork.setEscalationManager(escalationManager);
        statementOfWork.setVendorEscalationManager(vendorEscalationManager);
        statementOfWork.setAuthorizedSignature(authorizedSignature);
        statementOfWork.setStatus(true);

        StatementOfWork savedStatementOfWork = statementOfWorkRepository.save(statementOfWork);
        return mapToResponse(savedStatementOfWork);
    }

    @Override
    @Transactional
    public StatementOfWorkResponse updateStatementOfWork(Long id, StatementOfWorkRequest request) {
        // Validate required fields
        validateRequest(request);

        // Get existing statement of work
        StatementOfWork statementOfWork = statementOfWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statement of Work not found with id: " + id));

        // Get related entities if they've changed
        if (request.getLineManagerId() != null
                && !request.getLineManagerId().equals(statementOfWork.getLineManager().getId())) {
            LineManager lineManager = getLineManager(request.getLineManagerId());
            statementOfWork.setLineManager(lineManager);
        }

        if (request.getEscalationManagerId() != null
                && !request.getEscalationManagerId().equals(statementOfWork.getEscalationManager().getId())) {
            LineManager escalationManager = getLineManager(request.getEscalationManagerId());
            statementOfWork.setEscalationManager(escalationManager);
        }

        if (request.getVendorEscalationManagerId() != null && !request.getVendorEscalationManagerId()
                .equals(statementOfWork.getVendorEscalationManager().getId())) {
            LineManager vendorEscalationManager = getLineManager(request.getVendorEscalationManagerId());
            statementOfWork.setVendorEscalationManager(vendorEscalationManager);
        }

        if (request.getAuthorizedSignatureId() != null
                && !request.getAuthorizedSignatureId().equals(statementOfWork.getAuthorizedSignature().getId())) {
            AuthorizedSignature authorizedSignature = getAuthorizedSignature(request.getAuthorizedSignatureId());
            statementOfWork.setAuthorizedSignature(authorizedSignature);
        }

        // Update fields
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            statementOfWork.setName(request.getName());
        }

        if (request.getDescription() != null) {
            statementOfWork.setDescription(request.getDescription());
        }

        if (request.getStartDate() != null) {
            statementOfWork.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            statementOfWork.setEndDate(request.getEndDate());
        }

        if (request.getType() != null) {
            statementOfWork.setType(request.getType());
        }

        if (request.getFixedBidAmount() != null) {
            statementOfWork.setFixedBidAmount(request.getFixedBidAmount());
        }

        if (request.getProjectState() != null) {
            statementOfWork.setProjectState(request.getProjectState());
        }

        StatementOfWork updatedStatementOfWork = statementOfWorkRepository.save(statementOfWork);
        return mapToResponse(updatedStatementOfWork);
    }

    @Override
    @Transactional(readOnly = true)
    public StatementOfWorkResponse getStatementOfWorkById(Long id) {
        StatementOfWork statementOfWork = statementOfWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statement of Work not found with id: " + id));
        return mapToResponse(statementOfWork);
    }

    @Override
    @Transactional(readOnly = true)
    public StatementOfWorkResponse getStatementOfWorkByCustomId(String statementOfWorkId) {
        StatementOfWork statementOfWork = statementOfWorkRepository.findByStatementOfWorkId(statementOfWorkId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Statement of Work not found with ID: " + statementOfWorkId));
        return mapToResponse(statementOfWork);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementOfWorkResponse> getStatementOfWorksByName(String name) {
        return statementOfWorkRepository.findByNameContainingAndStatusTrue(name).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementOfWorkResponse> getAllActiveStatementOfWorks() {
        return statementOfWorkRepository.findByStatusTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStatementOfWork(Long id) {
        StatementOfWork statementOfWork = statementOfWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statement of Work not found with id: " + id));
        statementOfWork.setStatus(false);
        statementOfWorkRepository.save(statementOfWork);
    }

    @Override
    public String generateStatementOfWorkId(int year) {
        String prefix = "SOW-" + year + "-";
        Integer highestSequence = statementOfWorkRepository.findHighestSequenceNumberForYear(prefix);

        int nextSequence = (highestSequence == null) ? 1 : highestSequence + 1;
        return prefix + String.format("%03d", nextSequence);
    }

    /**
     * Validate the request data
     */
    private void validateRequest(StatementOfWorkRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }

        if (request.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required");
        }

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        if (request.getType() == null) {
            throw new IllegalArgumentException("Type is required");
        }

        if (request.getProjectState() == null) {
            throw new IllegalArgumentException("Project state is required");
        }

        if (request.getLineManagerId() == null) {
            throw new IllegalArgumentException("Line manager is required");
        }

        if (request.getEscalationManagerId() == null) {
            throw new IllegalArgumentException("Escalation manager is required");
        }

        if (request.getVendorEscalationManagerId() == null) {
            throw new IllegalArgumentException("Vendor escalation manager is required");
        }

        if (request.getAuthorizedSignatureId() == null) {
            throw new IllegalArgumentException("Authorized signature is required");
        }
    }

    /**
     * Get line manager by ID
     */
    private LineManager getLineManager(Long id) {
        return lineManagerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Line Manager not found with id: " + id));
    }

    /**
     * Get authorized signature by ID
     */
    private AuthorizedSignature getAuthorizedSignature(Long id) {
        return authorizedSignatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authorized Signature not found with id: " + id));
    }

    /**
     * Map entity to response DTO
     */
    private StatementOfWorkResponse mapToResponse(StatementOfWork statementOfWork) {
        StatementOfWorkResponse response = new StatementOfWorkResponse();
        response.setId(statementOfWork.getId());
        response.setStatementOfWorkId(statementOfWork.getStatementOfWorkId());
        response.setName(statementOfWork.getName());
        response.setDescription(statementOfWork.getDescription());
        response.setStartDate(statementOfWork.getStartDate());
        response.setEndDate(statementOfWork.getEndDate());
        response.setType(statementOfWork.getType());
        response.setTypeDisplayName(statementOfWork.getType().getDisplayName());
        response.setFixedBidAmount(statementOfWork.getFixedBidAmount());
        response.setProjectState(statementOfWork.getProjectState());
        response.setProjectStateDisplayName(statementOfWork.getProjectState().getDisplayName());

        // Map related entities
        LineManagerResponse lineManagerResponse = lineManagerService
                .getLineManagerById(statementOfWork.getLineManager().getId());
        response.setLineManager(lineManagerResponse);

        LineManagerResponse escalationManagerResponse = lineManagerService
                .getLineManagerById(statementOfWork.getEscalationManager().getId());
        response.setEscalationManager(escalationManagerResponse);

        LineManagerResponse vendorEscalationManagerResponse = lineManagerService
                .getLineManagerById(statementOfWork.getVendorEscalationManager().getId());
        response.setVendorEscalationManager(vendorEscalationManagerResponse);

        AuthorizedSignatureResponse authorizedSignatureResponse = authorizedSignatureService
                .getAuthorizedSignatureById(statementOfWork.getAuthorizedSignature().getId());
        response.setAuthorizedSignature(authorizedSignatureResponse);

        response.setStatus(statementOfWork.isStatus());
        return response;
    }
}