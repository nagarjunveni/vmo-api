package com.example.vmo.controller;

import com.example.vmo.dto.ActivitiesAndDeliverablesRequest;
import com.example.vmo.dto.ActivitiesAndDeliverablesResponse;
import com.example.vmo.service.ActivitiesAndDeliverablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities-and-deliverables")
@RequiredArgsConstructor
public class ActivitiesAndDeliverablesController {

    private final ActivitiesAndDeliverablesService activitiesAndDeliverablesService;

    /**
     * Create a new activities and deliverables record
     */
    @PostMapping
    public ResponseEntity<ActivitiesAndDeliverablesResponse> createActivitiesAndDeliverables(
            @RequestBody ActivitiesAndDeliverablesRequest request) {
        ActivitiesAndDeliverablesResponse response = activitiesAndDeliverablesService
                .createActivitiesAndDeliverables(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing activities and deliverables record
     */
    @PutMapping("/{id}")
    public ResponseEntity<ActivitiesAndDeliverablesResponse> updateActivitiesAndDeliverables(
            @PathVariable Long id,
            @RequestBody ActivitiesAndDeliverablesRequest request) {
        ActivitiesAndDeliverablesResponse response = activitiesAndDeliverablesService
                .updateActivitiesAndDeliverables(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get an activities and deliverables record by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActivitiesAndDeliverablesResponse> getActivitiesAndDeliverablesById(@PathVariable Long id) {
        ActivitiesAndDeliverablesResponse response = activitiesAndDeliverablesService
                .getActivitiesAndDeliverablesById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active activities and deliverables records
     */
    @GetMapping
    public ResponseEntity<List<ActivitiesAndDeliverablesResponse>> getAllActiveActivitiesAndDeliverables() {
        List<ActivitiesAndDeliverablesResponse> responses = activitiesAndDeliverablesService
                .getAllActiveActivitiesAndDeliverables();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get activities and deliverables records by SOW ID
     */
    @GetMapping("/sow/{sowId}")
    public ResponseEntity<List<ActivitiesAndDeliverablesResponse>> getActivitiesAndDeliverablesBySowId(
            @PathVariable Long sowId) {
        List<ActivitiesAndDeliverablesResponse> responses = activitiesAndDeliverablesService
                .getActivitiesAndDeliverablesBySowId(sowId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete an activities and deliverables record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivitiesAndDeliverables(@PathVariable Long id) {
        activitiesAndDeliverablesService.deleteActivitiesAndDeliverables(id);
        return ResponseEntity.noContent().build();
    }
}