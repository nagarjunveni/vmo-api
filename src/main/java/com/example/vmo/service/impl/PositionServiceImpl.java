package com.example.vmo.service.impl;

import com.example.vmo.dto.PositionRequest;
import com.example.vmo.dto.PositionResponse;
import com.example.vmo.model.Position;
import com.example.vmo.repository.PositionRepository;
import com.example.vmo.service.PositionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public PositionResponse createPosition(PositionRequest request) {
        validatePositionRequest(request);

        Position position = new Position();
        updatePositionFromRequest(position, request);

        Position savedPosition = positionRepository.save(position);
        return mapToPositionResponse(savedPosition);
    }

    @Override
    @Transactional
    public PositionResponse updatePosition(Long id, PositionRequest request) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));

        updatePositionFromRequest(position, request);

        Position updatedPosition = positionRepository.save(position);
        return mapToPositionResponse(updatedPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public PositionResponse getPositionById(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));

        return mapToPositionResponse(position);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PositionResponse> getAllActivePositions() {
        List<Position> positions = positionRepository.findByStatusTrue();
        return positions.stream()
                .map(this::mapToPositionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePosition(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));

        position.setStatus(false);
        positionRepository.save(position);
    }

    private void validatePositionRequest(PositionRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Position title is required");
        }

        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Position description is required");
        }

        if (request.getAmount() == null) {
            throw new IllegalArgumentException("Position amount is required");
        }

        if (request.getHourlyRate() == null) {
            throw new IllegalArgumentException("Position hourly rate is required");
        }

        if (request.getMonthlyRate() == null) {
            throw new IllegalArgumentException("Position monthly rate is required");
        }

        if (request.getSkills() == null || request.getSkills().trim().isEmpty()) {
            throw new IllegalArgumentException("Position skills are required");
        }

        if (request.getExpertise() == null || request.getExpertise().trim().isEmpty()) {
            throw new IllegalArgumentException("Position expertise is required");
        }
    }

    private void updatePositionFromRequest(Position position, PositionRequest request) {
        if (request.getTitle() != null) {
            position.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            position.setDescription(request.getDescription());
        }

        if (request.getAmount() != null) {
            position.setAmount(request.getAmount());
        }

        if (request.getHourlyRate() != null) {
            position.setHourlyRate(request.getHourlyRate());
        }

        if (request.getMonthlyRate() != null) {
            position.setMonthlyRate(request.getMonthlyRate());
        }

        if (request.getSkills() != null) {
            position.setSkills(request.getSkills());
        }

        if (request.getExpertise() != null) {
            position.setExpertise(request.getExpertise());
        }

        if (request.getStatus() != null) {
            position.setStatus(request.getStatus());
        }
    }

    private PositionResponse mapToPositionResponse(Position position) {
        return new PositionResponse(
                position.getId(),
                position.getTitle(),
                position.getDescription(),
                position.getAmount(),
                position.getHourlyRate(),
                position.getMonthlyRate(),
                position.getSkills(),
                position.getExpertise(),
                position.getCreatedDate(),
                position.getUpdatedDate(),
                position.isStatus());
    }
}