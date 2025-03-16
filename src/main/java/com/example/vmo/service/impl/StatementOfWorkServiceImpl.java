package com.example.vmo.service.impl;

import com.example.vmo.dto.*;
import com.example.vmo.enums.PositionType;
import com.example.vmo.model.AuthorizedSignature;
import com.example.vmo.model.LineManager;
import com.example.vmo.model.StatementOfWork;
import com.example.vmo.model.StatementOfWorkPosition;
import com.example.vmo.repository.AuthorizedSignatureRepository;
import com.example.vmo.repository.LineManagerRepository;
import com.example.vmo.repository.PositionRepository;
import com.example.vmo.repository.StatementOfWorkPositionRepository;
import com.example.vmo.repository.StatementOfWorkRepository;
import com.example.vmo.service.AuthorizedSignatureService;
import com.example.vmo.service.LineManagerService;
import com.example.vmo.service.StatementOfWorkService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatementOfWorkServiceImpl implements StatementOfWorkService {

    private final StatementOfWorkRepository statementOfWorkRepository;
    private final LineManagerRepository lineManagerRepository;
    private final AuthorizedSignatureRepository authorizedSignatureRepository;
    private final LineManagerService lineManagerService;
    private final AuthorizedSignatureService authorizedSignatureService;
    private final StatementOfWorkPositionRepository sowPositionRepository;
    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public StatementOfWorkResponse createStatementOfWork(StatementOfWorkRequest request) {
        // Validate required fields
        validateRequest(request);

        // Get related entities
        LineManager lineManager = getLineManager(request.getLineManagerId());
        LineManager csxEscalationManager = getLineManager(request.getCsxEscalationManagerId());
        LineManager compnovaEscalationManager = getLineManager(request.getCompnovaEscalationManagerId());
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
        statementOfWork.setCsxEscalationManager(csxEscalationManager);
        statementOfWork.setCompnovaEscalationManager(compnovaEscalationManager);
        statementOfWork.setAuthorizedSignature(authorizedSignature);
        statementOfWork.setStatus(true);

        // Save the statement of work
        StatementOfWork savedStatementOfWork = statementOfWorkRepository.save(statementOfWork);

        // Create positions if provided
        List<StatementOfWorkPositionResponse> positionResponses = new ArrayList<>();
        if (request.getPositions() != null && !request.getPositions().isEmpty()) {
            positionResponses = createOrUpdatePositions(savedStatementOfWork.getId(), request.getPositions());
        }

        // Map to response
        StatementOfWorkResponse response = mapToResponse(savedStatementOfWork);
        response.setPositions(positionResponses);

        // Calculate position counts
        updatePositionCounts(response);

        return response;
    }

    @Override
    @Transactional
    public StatementOfWorkResponse updateStatementOfWork(Long id, StatementOfWorkRequest request) {
        // Find the statement of work
        StatementOfWork statementOfWork = statementOfWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statement of Work not found with id: " + id));

        // Update fields if provided
        if (request.getName() != null) {
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

        if (request.getLineManagerId() != null) {
            LineManager lineManager = getLineManager(request.getLineManagerId());
            statementOfWork.setLineManager(lineManager);
        }

        if (request.getCsxEscalationManagerId() != null) {
            LineManager csxEscalationManager = getLineManager(request.getCsxEscalationManagerId());
            statementOfWork.setCsxEscalationManager(csxEscalationManager);
        }

        if (request.getCompnovaEscalationManagerId() != null) {
            LineManager compnovaEscalationManager = getLineManager(request.getCompnovaEscalationManagerId());
            statementOfWork.setCompnovaEscalationManager(compnovaEscalationManager);
        }

        if (request.getAuthorizedSignatureId() != null) {
            AuthorizedSignature authorizedSignature = getAuthorizedSignature(request.getAuthorizedSignatureId());
            statementOfWork.setAuthorizedSignature(authorizedSignature);
        }

        // Save the updated statement of work
        StatementOfWork updatedStatementOfWork = statementOfWorkRepository.save(statementOfWork);

        // Update positions if provided
        List<StatementOfWorkPositionResponse> positionResponses = new ArrayList<>();
        if (request.getPositions() != null) {
            positionResponses = createOrUpdatePositions(updatedStatementOfWork.getId(), request.getPositions());
        } else {
            // If no positions provided, get existing positions
            positionResponses = getPositionsForStatementOfWork(updatedStatementOfWork.getId());
        }

        // Map to response
        StatementOfWorkResponse response = mapToResponse(updatedStatementOfWork);
        response.setPositions(positionResponses);

        // Calculate position counts
        updatePositionCounts(response);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public StatementOfWorkResponse getStatementOfWorkById(Long id) {
        StatementOfWork statementOfWork = statementOfWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statement of Work not found with id: " + id));

        StatementOfWorkResponse response = mapToResponse(statementOfWork);

        // Get positions for this SOW
        List<StatementOfWorkPositionResponse> positions = getPositionsForStatementOfWork(id);
        response.setPositions(positions);

        // Calculate position counts
        updatePositionCounts(response);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public StatementOfWorkResponse getStatementOfWorkByCustomId(String statementOfWorkId) {
        StatementOfWork statementOfWork = statementOfWorkRepository.findByStatementOfWorkId(statementOfWorkId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Statement of Work not found with custom id: " + statementOfWorkId));

        StatementOfWorkResponse response = mapToResponse(statementOfWork);

        // Get positions for this SOW
        List<StatementOfWorkPositionResponse> positions = getPositionsForStatementOfWork(statementOfWork.getId());
        response.setPositions(positions);

        // Calculate position counts
        updatePositionCounts(response);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementOfWorkResponse> getStatementOfWorksByName(String name) {
        List<StatementOfWork> statementOfWorks = statementOfWorkRepository.findByNameContainingAndStatusTrue(name);

        return statementOfWorks.stream()
                .map(sow -> {
                    StatementOfWorkResponse response = mapToResponse(sow);

                    // Calculate position counts
                    Map<PositionType, Long> counts = sowPositionRepository.findBySowIdAndStatusTrue(sow.getId())
                            .stream()
                            .collect(Collectors.groupingBy(StatementOfWorkPosition::getType, Collectors.counting()));

                    response.setOnsiteCount((int) counts.getOrDefault(PositionType.Onsite, 0L).longValue());
                    response.setOffshoreCount((int) counts.getOrDefault(PositionType.Offshore, 0L).longValue());

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementOfWorkResponse> getAllActiveStatementOfWorks() {
        List<StatementOfWork> statementOfWorks = statementOfWorkRepository.findByStatusTrue();

        return statementOfWorks.stream()
                .map(sow -> {
                    StatementOfWorkResponse response = mapToResponse(sow);

                    // Calculate position counts
                    Map<PositionType, Long> counts = sowPositionRepository.findBySowIdAndStatusTrue(sow.getId())
                            .stream()
                            .collect(Collectors.groupingBy(StatementOfWorkPosition::getType, Collectors.counting()));

                    response.setOnsiteCount((int) counts.getOrDefault(PositionType.Onsite, 0L).longValue());
                    response.setOffshoreCount((int) counts.getOrDefault(PositionType.Offshore, 0L).longValue());

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStatementOfWork(Long id) {
        StatementOfWork statementOfWork = statementOfWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statement of Work not found with id: " + id));

        statementOfWork.setStatus(false);
        statementOfWorkRepository.save(statementOfWork);

        // Also soft delete all associated positions
        List<StatementOfWorkPosition> positions = sowPositionRepository.findBySowIdAndStatusTrue(id);
        positions.forEach(position -> {
            position.setStatus(false);
            sowPositionRepository.save(position);
        });
    }

    @Override
    public String generateStatementOfWorkId(int year) {
        // Get the count of SOWs for the given year
        int count = statementOfWorkRepository.countByStatementOfWorkIdStartingWith("SOW-" + year);

        // Format: SOW-YYYY-XXX where XXX is a sequential number starting from 001
        return String.format("SOW-%d-%03d", year, count + 1);
    }

    private void validateRequest(StatementOfWorkRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Statement of Work name is required");
        }

        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Statement of Work description is required");
        }

        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("Statement of Work start date is required");
        }

        if (request.getEndDate() == null) {
            throw new IllegalArgumentException("Statement of Work end date is required");
        }

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        if (request.getType() == null) {
            throw new IllegalArgumentException("Statement of Work type is required");
        }

        if (request.getProjectState() == null) {
            throw new IllegalArgumentException("Statement of Work project state is required");
        }

        if (request.getLineManagerId() == null) {
            throw new IllegalArgumentException("Line Manager ID is required");
        }

        if (request.getCsxEscalationManagerId() == null) {
            throw new IllegalArgumentException("CSX Escalation Manager ID is required");
        }

        if (request.getCompnovaEscalationManagerId() == null) {
            throw new IllegalArgumentException("Compnova Escalation Manager ID is required");
        }

        if (request.getAuthorizedSignatureId() == null) {
            throw new IllegalArgumentException("Authorized Signature ID is required");
        }

        // Validate positions if provided
        if (request.getPositions() != null) {
            for (StatementOfWorkPositionRequest position : request.getPositions()) {
                if (position.getPositionId() == null) {
                    throw new IllegalArgumentException("Position ID is required for all positions");
                }

                if (position.getType() == null) {
                    throw new IllegalArgumentException("Position type is required for all positions");
                }

                // Validate that the position exists
                if (!positionRepository.existsById(position.getPositionId())) {
                    throw new EntityNotFoundException("Position not found with id: " + position.getPositionId());
                }
            }
        }
    }

    private LineManager getLineManager(Long id) {
        return lineManagerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Line Manager not found with id: " + id));
    }

    private AuthorizedSignature getAuthorizedSignature(Long id) {
        return authorizedSignatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authorized Signature not found with id: " + id));
    }

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
        response.setStatus(statementOfWork.isStatus());

        // Get related entities
        LineManagerResponse lineManager = lineManagerService
                .getLineManagerById(statementOfWork.getLineManager().getId());
        LineManagerResponse csxEscalationManager = lineManagerService
                .getLineManagerById(statementOfWork.getCsxEscalationManager().getId());
        LineManagerResponse compnovaEscalationManager = lineManagerService
                .getLineManagerById(statementOfWork.getCompnovaEscalationManager().getId());
        AuthorizedSignatureResponse authorizedSignature = authorizedSignatureService
                .getAuthorizedSignatureById(statementOfWork.getAuthorizedSignature().getId());

        response.setLineManager(lineManager);
        response.setCsxEscalationManager(csxEscalationManager);
        response.setCompnovaEscalationManager(compnovaEscalationManager);
        response.setAuthorizedSignature(authorizedSignature);

        return response;
    }

    private List<StatementOfWorkPositionResponse> createOrUpdatePositions(Long sowId,
            List<StatementOfWorkPositionRequest> positionRequests) {
        List<StatementOfWorkPositionResponse> responses = new ArrayList<>();

        for (StatementOfWorkPositionRequest request : positionRequests) {
            StatementOfWorkPosition position;

            // If id is 0 or null, create a new position
            if (request.getId() == null || request.getId() == 0) {
                position = new StatementOfWorkPosition();
                position.setSowId(sowId);
                position.setPositionId(request.getPositionId());
                position.setType(request.getType());
                position.setStatus(request.getStatus() != null ? request.getStatus() : true);
            } else {
                // Otherwise, update existing position
                position = sowPositionRepository.findById(request.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "StatementOfWorkPosition not found with id: " + request.getId()));

                // Only update if the position belongs to this SOW
                if (!position.getSowId().equals(sowId)) {
                    throw new IllegalArgumentException("Position with id " + request.getId()
                            + " does not belong to Statement of Work with id " + sowId);
                }

                if (request.getPositionId() != null) {
                    position.setPositionId(request.getPositionId());
                }

                if (request.getType() != null) {
                    position.setType(request.getType());
                }

                if (request.getStatus() != null) {
                    position.setStatus(request.getStatus());
                }
            }

            // Save the position
            StatementOfWorkPosition savedPosition = sowPositionRepository.save(position);

            // Map to response
            StatementOfWorkPositionResponse response = new StatementOfWorkPositionResponse();
            response.setId(savedPosition.getId());
            response.setSowId(savedPosition.getSowId());
            response.setPositionId(savedPosition.getPositionId());
            response.setType(savedPosition.getType());
            response.setStatus(savedPosition.isStatus());

            // Fetch and set the position details
            positionRepository.findById(savedPosition.getPositionId())
                    .ifPresent(pos -> {
                        PositionResponse positionResponse = new PositionResponse(
                                pos.getId(),
                                pos.getTitle(),
                                pos.getDescription(),
                                pos.getAmount(),
                                pos.getHourlyRate(),
                                pos.getMonthlyRate(),
                                pos.getSkills(),
                                pos.getExpertise(),
                                pos.getCreatedDate(),
                                pos.getUpdatedDate(),
                                pos.isStatus());
                        response.setPosition(positionResponse);
                    });

            responses.add(response);
        }

        return responses;
    }

    private List<StatementOfWorkPositionResponse> getPositionsForStatementOfWork(Long sowId) {
        List<StatementOfWorkPosition> positions = sowPositionRepository.findBySowIdAndStatusTrue(sowId);

        return positions.stream()
                .map(position -> {
                    StatementOfWorkPositionResponse response = new StatementOfWorkPositionResponse();
                    response.setId(position.getId());
                    response.setSowId(position.getSowId());
                    response.setPositionId(position.getPositionId());
                    response.setType(position.getType());
                    response.setStatus(position.isStatus());

                    // Fetch and set the position details
                    positionRepository.findById(position.getPositionId())
                            .ifPresent(pos -> {
                                PositionResponse positionResponse = new PositionResponse(
                                        pos.getId(),
                                        pos.getTitle(),
                                        pos.getDescription(),
                                        pos.getAmount(),
                                        pos.getHourlyRate(),
                                        pos.getMonthlyRate(),
                                        pos.getSkills(),
                                        pos.getExpertise(),
                                        pos.getCreatedDate(),
                                        pos.getUpdatedDate(),
                                        pos.isStatus());
                                response.setPosition(positionResponse);
                            });

                    return response;
                })
                .collect(Collectors.toList());
    }

    private void updatePositionCounts(StatementOfWorkResponse response) {
        if (response.getPositions() != null) {
            response.setOnsiteCount((int) response.getPositions().stream()
                    .filter(p -> p.getType() == PositionType.Onsite && p.isStatus())
                    .count());

            response.setOffshoreCount((int) response.getPositions().stream()
                    .filter(p -> p.getType() == PositionType.Offshore && p.isStatus())
                    .count());
        } else {
            response.setOnsiteCount(0);
            response.setOffshoreCount(0);
        }
    }
}