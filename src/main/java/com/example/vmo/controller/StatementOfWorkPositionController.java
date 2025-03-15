package com.example.vmo.controller;

import com.example.vmo.dto.StatementOfWorkPositionRequest;
import com.example.vmo.dto.StatementOfWorkPositionResponse;
import com.example.vmo.service.StatementOfWorkPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statement-of-work-positions")
@RequiredArgsConstructor
public class StatementOfWorkPositionController {

    private final StatementOfWorkPositionService sowPositionService;

    /**
     * Create a new StatementOfWorkPosition
     */
    @PostMapping
    public ResponseEntity<StatementOfWorkPositionResponse> createStatementOfWorkPosition(
            @RequestBody StatementOfWorkPositionRequest request) {
        StatementOfWorkPositionResponse response = sowPositionService.createStatementOfWorkPosition(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing StatementOfWorkPosition
     */
    @PutMapping("/{id}")
    public ResponseEntity<StatementOfWorkPositionResponse> updateStatementOfWorkPosition(
            @PathVariable Long id,
            @RequestBody StatementOfWorkPositionRequest request) {
        StatementOfWorkPositionResponse response = sowPositionService.updateStatementOfWorkPosition(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a StatementOfWorkPosition by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<StatementOfWorkPositionResponse> getStatementOfWorkPositionById(@PathVariable Long id) {
        StatementOfWorkPositionResponse response = sowPositionService.getStatementOfWorkPositionById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active StatementOfWorkPositions
     */
    @GetMapping
    public ResponseEntity<List<StatementOfWorkPositionResponse>> getAllActiveStatementOfWorkPositions() {
        List<StatementOfWorkPositionResponse> responses = sowPositionService.getAllActiveStatementOfWorkPositions();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get all active StatementOfWorkPositions by SOW ID
     */
    @GetMapping("/sow/{sowId}")
    public ResponseEntity<List<StatementOfWorkPositionResponse>> getStatementOfWorkPositionsBySowId(
            @PathVariable Long sowId) {
        List<StatementOfWorkPositionResponse> responses = sowPositionService.getStatementOfWorkPositionsBySowId(sowId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete a StatementOfWorkPosition
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatementOfWorkPosition(@PathVariable Long id) {
        sowPositionService.deleteStatementOfWorkPosition(id);
        return ResponseEntity.noContent().build();
    }
}