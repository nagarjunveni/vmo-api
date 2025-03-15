package com.example.vmo.controller;

import com.example.vmo.dto.PositionRequest;
import com.example.vmo.dto.PositionResponse;
import com.example.vmo.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    /**
     * Create a new position
     */
    @PostMapping
    public ResponseEntity<PositionResponse> createPosition(@RequestBody PositionRequest request) {
        PositionResponse response = positionService.createPosition(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing position
     */
    @PutMapping("/{id}")
    public ResponseEntity<PositionResponse> updatePosition(
            @PathVariable Long id,
            @RequestBody PositionRequest request) {
        PositionResponse response = positionService.updatePosition(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a position by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PositionResponse> getPositionById(@PathVariable Long id) {
        PositionResponse response = positionService.getPositionById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active positions
     */
    @GetMapping
    public ResponseEntity<List<PositionResponse>> getAllActivePositions() {
        List<PositionResponse> responses = positionService.getAllActivePositions();
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete a position
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}