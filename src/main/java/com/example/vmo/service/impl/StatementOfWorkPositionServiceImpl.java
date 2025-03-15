package com.example.vmo.service.impl;

import com.example.vmo.dto.PositionResponse;
import com.example.vmo.dto.StatementOfWorkPositionRequest;
import com.example.vmo.dto.StatementOfWorkPositionResponse;
import com.example.vmo.dto.StatementOfWorkResponse;
import com.example.vmo.model.Position;
import com.example.vmo.model.StatementOfWork;
import com.example.vmo.model.StatementOfWorkPosition;
import com.example.vmo.repository.PositionRepository;
import com.example.vmo.repository.StatementOfWorkPositionRepository;
import com.example.vmo.repository.StatementOfWorkRepository;
import com.example.vmo.service.StatementOfWorkPositionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatementOfWorkPositionServiceImpl implements StatementOfWorkPositionService {

    private final StatementOfWorkPositionRepository sowPositionRepository;
    private final StatementOfWorkRepository sowRepository;
    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public StatementOfWorkPositionResponse createStatementOfWorkPosition(StatementOfWorkPositionRequest request) {
        validateStatementOfWorkPositionRequest(request);

        StatementOfWorkPosition sowPosition = new StatementOfWorkPosition();
        updateStatementOfWorkPositionFromRequest(sowPosition, request);

        StatementOfWorkPosition savedSowPosition = sowPositionRepository.save(sowPosition);
        return mapToStatementOfWorkPositionResponse(savedSowPosition);
    }

    @Override
    @Transactional
    public StatementOfWorkPositionResponse updateStatementOfWorkPosition(Long id,
            StatementOfWorkPositionRequest request) {
        StatementOfWorkPosition sowPosition = sowPositionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StatementOfWorkPosition not found with id: " + id));

        if (request.getSowId() != null) {
            validateStatementOfWork(request.getSowId());
        }

        if (request.getPositionId() != null) {
            validatePosition(request.getPositionId());
        }

        updateStatementOfWorkPositionFromRequest(sowPosition, request);

        StatementOfWorkPosition updatedSowPosition = sowPositionRepository.save(sowPosition);
        return mapToStatementOfWorkPositionResponse(updatedSowPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public StatementOfWorkPositionResponse getStatementOfWorkPositionById(Long id) {
        StatementOfWorkPosition sowPosition = sowPositionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StatementOfWorkPosition not found with id: " + id));

        return mapToStatementOfWorkPositionResponse(sowPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementOfWorkPositionResponse> getAllActiveStatementOfWorkPositions() {
        List<StatementOfWorkPosition> sowPositions = sowPositionRepository.findByStatusTrue();
        return sowPositions.stream()
                .map(this::mapToStatementOfWorkPositionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementOfWorkPositionResponse> getStatementOfWorkPositionsBySowId(Long sowId) {
        List<StatementOfWorkPosition> sowPositions = sowPositionRepository.findBySowIdAndStatusTrue(sowId);
        return sowPositions.stream()
                .map(this::mapToStatementOfWorkPositionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStatementOfWorkPosition(Long id) {
        StatementOfWorkPosition sowPosition = sowPositionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StatementOfWorkPosition not found with id: " + id));

        sowPosition.setStatus(false);
        sowPositionRepository.save(sowPosition);
    }

    private void validateStatementOfWorkPositionRequest(StatementOfWorkPositionRequest request) {
        if (request.getSowId() == null) {
            throw new IllegalArgumentException("Statement of Work ID is required");
        }

        if (request.getPositionId() == null) {
            throw new IllegalArgumentException("Position ID is required");
        }

        if (request.getType() == null) {
            throw new IllegalArgumentException("Position type is required");
        }

        validateStatementOfWork(request.getSowId());
        validatePosition(request.getPositionId());
    }

    private void validateStatementOfWork(Long sowId) {
        if (!sowRepository.existsById(sowId)) {
            throw new EntityNotFoundException("Statement of Work not found with id: " + sowId);
        }
    }

    private void validatePosition(Long positionId) {
        if (!positionRepository.existsById(positionId)) {
            throw new EntityNotFoundException("Position not found with id: " + positionId);
        }
    }

    private void updateStatementOfWorkPositionFromRequest(StatementOfWorkPosition sowPosition,
            StatementOfWorkPositionRequest request) {
        if (request.getSowId() != null) {
            sowPosition.setSowId(request.getSowId());
        }

        if (request.getPositionId() != null) {
            sowPosition.setPositionId(request.getPositionId());
        }

        if (request.getType() != null) {
            sowPosition.setType(request.getType());
        }

        if (request.getStatus() != null) {
            sowPosition.setStatus(request.getStatus());
        }
    }

    private StatementOfWorkPositionResponse mapToStatementOfWorkPositionResponse(StatementOfWorkPosition sowPosition) {
        StatementOfWorkPositionResponse response = new StatementOfWorkPositionResponse();
        response.setId(sowPosition.getId());
        response.setSowId(sowPosition.getSowId());
        response.setPositionId(sowPosition.getPositionId());
        response.setType(sowPosition.getType());
        response.setStatus(sowPosition.isStatus());

        // We don't load the full objects by default to avoid circular references
        // and to keep the response lightweight

        return response;
    }
}