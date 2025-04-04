package com.example.vmo.service.impl;

import com.example.vmo.dto.MilepostRequest;
import com.example.vmo.dto.MilepostResponse;
import com.example.vmo.model.Milepost;
import com.example.vmo.repository.MilepostRepository;
import com.example.vmo.repository.StatementOfWorkRepository;
import com.example.vmo.service.MilepostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MilepostServiceImpl implements MilepostService {

    private final MilepostRepository milepostRepository;
    private final StatementOfWorkRepository statementOfWorkRepository;

    @Override
    @Transactional
    public MilepostResponse createMilepost(MilepostRequest request) {
        validateMilepostRequest(request);

        Milepost milepost = new Milepost();
        updateMilepostFromRequest(milepost, request);

        Milepost savedMilepost = milepostRepository.save(milepost);
        return mapToMilepostResponse(savedMilepost);
    }

    @Override
    @Transactional
    public MilepostResponse updateMilepost(Long id, MilepostRequest request) {
        Milepost milepost = milepostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Milepost not found with id: " + id));

        // Validate SOW if provided
        if (request.getSowId() != null && !statementOfWorkRepository.existsById(request.getSowId())) {
            throw new EntityNotFoundException("Statement of work not found with id: " + request.getSowId());
        }

        // Validate due date if provided
        if (request.getDueDate() != null && request.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be a future date");
        }

        updateMilepostFromRequest(milepost, request);

        Milepost updatedMilepost = milepostRepository.save(milepost);
        return mapToMilepostResponse(updatedMilepost);
    }

    @Override
    @Transactional(readOnly = true)
    public MilepostResponse getMilepostById(Long id) {
        Milepost milepost = milepostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Milepost not found with id: " + id));

        return mapToMilepostResponse(milepost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MilepostResponse> getAllActiveMileposts() {
        List<Milepost> mileposts = milepostRepository.findByStatusTrue();
        return mileposts.stream()
                .map(this::mapToMilepostResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MilepostResponse> getMilepostsBySowId(Long sowId) {
        // Validate that the SOW exists
        if (!statementOfWorkRepository.existsById(sowId)) {
            throw new EntityNotFoundException("Statement of work not found with id: " + sowId);
        }

        List<Milepost> mileposts = milepostRepository.findBySowIdAndStatusTrue(sowId);
        return mileposts.stream()
                .map(this::mapToMilepostResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteMilepost(Long id) {
        Milepost milepost = milepostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Milepost not found with id: " + id));

        milepost.setStatus(false);
        milepostRepository.save(milepost);
    }

    private void validateMilepostRequest(MilepostRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Milepost name is required");
        }

        if (request.getDueDate() == null) {
            throw new IllegalArgumentException("Due date is required");
        }

        if (request.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be a future date");
        }

        if (request.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (request.getSowId() == null) {
            throw new IllegalArgumentException("SOW ID is required");
        }

        if (!statementOfWorkRepository.existsById(request.getSowId())) {
            throw new EntityNotFoundException("Statement of work not found with id: " + request.getSowId());
        }
    }

    private void updateMilepostFromRequest(Milepost milepost, MilepostRequest request) {
        if (request.getName() != null) {
            milepost.setName(request.getName());
        }

        if (request.getDueDate() != null) {
            milepost.setDueDate(request.getDueDate());
        }

        if (request.getAmount() != null) {
            milepost.setAmount(request.getAmount());
        }

        if (request.getDescription() != null) {
            milepost.setDescription(request.getDescription());
        }

        if (request.getSowId() != null) {
            milepost.setSowId(request.getSowId());
        }
    }

    private MilepostResponse mapToMilepostResponse(Milepost milepost) {
        MilepostResponse response = new MilepostResponse();
        response.setId(milepost.getId());
        response.setName(milepost.getName());
        response.setDueDate(milepost.getDueDate());
        response.setAmount(milepost.getAmount());
        response.setDescription(milepost.getDescription());
        response.setSowId(milepost.getSowId());
        response.setStatus(milepost.isStatus());
        response.setCreatedDate(milepost.getCreatedDate());
        response.setUpdatedDate(milepost.getUpdatedDate());
        return response;
    }
}