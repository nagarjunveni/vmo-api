package com.example.vmo.controller;

import com.example.vmo.dto.StatementOfWorkRequest;
import com.example.vmo.dto.StatementOfWorkResponse;
import com.example.vmo.service.StatementOfWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statement-of-works")
@RequiredArgsConstructor
public class StatementOfWorkController {

    private final StatementOfWorkService statementOfWorkService;

    /**
     * Create a new statement of work with positions
     */
    @PostMapping
    public ResponseEntity<StatementOfWorkResponse> createStatementOfWork(@RequestBody StatementOfWorkRequest request) {
        StatementOfWorkResponse response = statementOfWorkService.createStatementOfWork(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing statement of work with positions
     */
    @PutMapping("/{id}")
    public ResponseEntity<StatementOfWorkResponse> updateStatementOfWork(
            @PathVariable Long id,
            @RequestBody StatementOfWorkRequest request) {
        StatementOfWorkResponse response = statementOfWorkService.updateStatementOfWork(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a statement of work by ID with positions
     */
    @GetMapping("/{id}")
    public ResponseEntity<StatementOfWorkResponse> getStatementOfWorkById(@PathVariable Long id) {
        StatementOfWorkResponse response = statementOfWorkService.getStatementOfWorkById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a statement of work by custom ID
     */
    @GetMapping("/custom/{statementOfWorkId}")
    public ResponseEntity<StatementOfWorkResponse> getStatementOfWorkByCustomId(
            @PathVariable String statementOfWorkId) {
        StatementOfWorkResponse response = statementOfWorkService.getStatementOfWorkByCustomId(statementOfWorkId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get statements of work by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<StatementOfWorkResponse>> getStatementOfWorksByName(@RequestParam String name) {
        List<StatementOfWorkResponse> responses = statementOfWorkService.getStatementOfWorksByName(name);
        return ResponseEntity.ok(responses);
    }

    /**
     * Get all active statements of work with onsite and offshore counts
     */
    @GetMapping
    public ResponseEntity<List<StatementOfWorkResponse>> getAllActiveStatementOfWorks() {
        List<StatementOfWorkResponse> responses = statementOfWorkService.getAllActiveStatementOfWorks();
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete a statement of work
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatementOfWork(@PathVariable Long id) {
        statementOfWorkService.deleteStatementOfWork(id);
        return ResponseEntity.noContent().build();
    }
}