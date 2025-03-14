package com.example.vmo.controller;

import com.example.vmo.dto.VendorRequest;
import com.example.vmo.dto.VendorResponse;
import com.example.vmo.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    /**
     * Create a new vendor
     */
    @PostMapping
    public ResponseEntity<VendorResponse> createVendor(@RequestBody VendorRequest request) {
        VendorResponse response = vendorService.createVendor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing vendor
     */
    @PutMapping("/{id}")
    public ResponseEntity<VendorResponse> updateVendor(
            @PathVariable Long id,
            @RequestBody VendorRequest request) {
        VendorResponse response = vendorService.updateVendor(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a vendor by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<VendorResponse> getVendorById(@PathVariable Long id) {
        VendorResponse response = vendorService.getVendorById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a vendor by employee identification number
     */
    @GetMapping("/ein/{employeeIdentificationNumber}")
    public ResponseEntity<VendorResponse> getVendorByEmployeeIdentificationNumber(
            @PathVariable String employeeIdentificationNumber) {
        VendorResponse response = vendorService.getVendorByEmployeeIdentificationNumber(employeeIdentificationNumber);
        return ResponseEntity.ok(response);
    }

    /**
     * Get vendors by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<VendorResponse>> getVendorsByName(@RequestParam String name) {
        List<VendorResponse> responses = vendorService.getVendorsByName(name);
        return ResponseEntity.ok(responses);
    }

    /**
     * Get all active vendors
     */
    @GetMapping
    public ResponseEntity<List<VendorResponse>> getAllActiveVendors() {
        List<VendorResponse> responses = vendorService.getAllActiveVendors();
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete a vendor
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }
}