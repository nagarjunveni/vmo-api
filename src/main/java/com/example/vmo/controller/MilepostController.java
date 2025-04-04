package com.example.vmo.controller;

import com.example.vmo.dto.MilepostRequest;
import com.example.vmo.dto.MilepostResponse;
import com.example.vmo.service.MilepostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mileposts")
@RequiredArgsConstructor
public class MilepostController {

    private final MilepostService milepostService;

    /**
     * Create a new milepost
     */
    @PostMapping
    public ResponseEntity<MilepostResponse> createMilepost(@RequestBody MilepostRequest request) {
        MilepostResponse response = milepostService.createMilepost(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing milepost
     */
    @PutMapping("/{id}")
    public ResponseEntity<MilepostResponse> updateMilepost(
            @PathVariable Long id,
            @RequestBody MilepostRequest request) {
        MilepostResponse response = milepostService.updateMilepost(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a milepost by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MilepostResponse> getMilepostById(@PathVariable Long id) {
        MilepostResponse response = milepostService.getMilepostById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active mileposts
     */
    @GetMapping
    public ResponseEntity<List<MilepostResponse>> getAllActiveMileposts() {
        List<MilepostResponse> responses = milepostService.getAllActiveMileposts();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get mileposts by SOW ID
     */
    @GetMapping("/sow/{sowId}")
    public ResponseEntity<List<MilepostResponse>> getMilepostsBySowId(@PathVariable Long sowId) {
        List<MilepostResponse> responses = milepostService.getMilepostsBySowId(sowId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete a milepost
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilepost(@PathVariable Long id) {
        milepostService.deleteMilepost(id);
        return ResponseEntity.noContent().build();
    }
}