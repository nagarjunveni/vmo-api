package com.example.vmo.controller;

import com.example.vmo.dto.LineManagerRequest;
import com.example.vmo.dto.LineManagerResponse;
import com.example.vmo.enums.LineManagerType;
import com.example.vmo.service.LineManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/line-managers")
@RequiredArgsConstructor
public class LineManagerController {

    private final LineManagerService lineManagerService;

    /**
     * Create a new line manager
     */
    @PostMapping
    public ResponseEntity<LineManagerResponse> createLineManager(@RequestBody LineManagerRequest request) {
        LineManagerResponse response = lineManagerService.createLineManager(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing line manager
     */
    @PutMapping("/{id}")
    public ResponseEntity<LineManagerResponse> updateLineManager(
            @PathVariable Long id,
            @RequestBody LineManagerRequest request) {
        LineManagerResponse response = lineManagerService.updateLineManager(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a line manager by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<LineManagerResponse> getLineManagerById(@PathVariable Long id) {
        LineManagerResponse response = lineManagerService.getLineManagerById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active line managers
     */
    @GetMapping
    public ResponseEntity<List<LineManagerResponse>> getAllActiveLineManagers() {
        List<LineManagerResponse> responses = lineManagerService.getAllActiveLineManagers();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get line managers by type
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LineManagerResponse>> getLineManagersByType(@PathVariable LineManagerType type) {
        List<LineManagerResponse> responses = lineManagerService.getLineManagersByType(type);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete a line manager
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLineManager(@PathVariable Long id) {
        lineManagerService.deleteLineManager(id);
        return ResponseEntity.noContent().build();
    }
}