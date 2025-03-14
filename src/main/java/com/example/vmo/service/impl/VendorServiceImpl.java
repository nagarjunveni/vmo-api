package com.example.vmo.service.impl;

import com.example.vmo.dto.VendorRequest;
import com.example.vmo.dto.VendorResponse;
import com.example.vmo.model.Vendor;
import com.example.vmo.repository.VendorRepository;
import com.example.vmo.service.VendorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    @Override
    @Transactional
    public VendorResponse createVendor(VendorRequest request) {
        // Validate request
        validateVendorRequest(request);

        // Check if vendor with the same EIN already exists
        if (vendorRepository.existsByEmployeeIdentificationNumber(request.getEmployeeIdentificationNumber())) {
            throw new IllegalArgumentException("Vendor with employee identification number "
                    + request.getEmployeeIdentificationNumber() + " already exists");
        }

        // Create new vendor
        Vendor vendor = new Vendor();
        vendor.setEmployeeIdentificationNumber(request.getEmployeeIdentificationNumber());
        vendor.setCompanyName(request.getCompanyName());
        vendor.setFirstName(request.getFirstName());
        vendor.setMiddleName(request.getMiddleName());
        vendor.setLastName(request.getLastName());
        vendor.setEmail(request.getEmail());
        vendor.setContactNumber(request.getContactNumber());
        vendor.setLocation(request.getLocation());
        vendor.setRating(request.getRating());
        vendor.setCommission(request.getCommission());
        vendor.setStatus(true);

        Vendor savedVendor = vendorRepository.save(vendor);
        return mapToResponse(savedVendor);
    }

    @Override
    @Transactional
    public VendorResponse updateVendor(Long id, VendorRequest request) {
        // Validate request
        validateVendorRequest(request);

        // Get existing vendor
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + id));

        // Check if EIN is being changed and if it already exists
        if (!vendor.getEmployeeIdentificationNumber().equals(request.getEmployeeIdentificationNumber()) &&
                vendorRepository.existsByEmployeeIdentificationNumber(request.getEmployeeIdentificationNumber())) {
            throw new IllegalArgumentException("Vendor with employee identification number "
                    + request.getEmployeeIdentificationNumber() + " already exists");
        }

        // Update vendor fields
        vendor.setEmployeeIdentificationNumber(request.getEmployeeIdentificationNumber());
        vendor.setCompanyName(request.getCompanyName());
        vendor.setFirstName(request.getFirstName());
        vendor.setMiddleName(request.getMiddleName());
        vendor.setLastName(request.getLastName());
        vendor.setLocation(request.getLocation());
        vendor.setRating(request.getRating());
        vendor.setCommission(request.getCommission());
        vendor.setEmail(request.getEmail());
        vendor.setContactNumber(request.getContactNumber());

        Vendor updatedVendor = vendorRepository.save(vendor);
        return mapToResponse(updatedVendor);
    }

    @Override
    @Transactional(readOnly = true)
    public VendorResponse getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + id));
        return mapToResponse(vendor);
    }

    @Override
    @Transactional(readOnly = true)
    public VendorResponse getVendorByEmployeeIdentificationNumber(String employeeIdentificationNumber) {
        Vendor vendor = vendorRepository.findByEmployeeIdentificationNumber(employeeIdentificationNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Vendor not found with employee identification number: " + employeeIdentificationNumber));
        return mapToResponse(vendor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendorResponse> getVendorsByName(String name) {
        return vendorRepository.findByCompanyNameContainingAndStatusTrue(name).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendorResponse> getAllActiveVendors() {
        return vendorRepository.findByStatusTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + id));
        vendor.setStatus(false);
        vendorRepository.save(vendor);
    }

    private void validateVendorRequest(VendorRequest request) {
        if (request.getEmployeeIdentificationNumber() == null
                || request.getEmployeeIdentificationNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee identification number is required");
        }

        if (request.getCompanyName() == null || request.getCompanyName().trim().isEmpty()) {
            throw new IllegalArgumentException("Vendor name is required");
        }

        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (request.getContactNumber() == null || request.getContactNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number is required");
        }

        if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Location is required");
        }

        if (request.getRating() < 0 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        if (request.getCommission() < 0) {
            throw new IllegalArgumentException("Commission cannot be negative");
        }

        if (request.getCommission() > 100) {
            throw new IllegalArgumentException("Commission cannot be greater than 100");
        }
    }

    private VendorResponse mapToResponse(Vendor vendor) {
        VendorResponse response = new VendorResponse();
        response.setId(vendor.getId());
        response.setEmployeeIdentificationNumber(vendor.getEmployeeIdentificationNumber());
        response.setCompanyName(vendor.getCompanyName());
        response.setFirstName(vendor.getFirstName());
        response.setMiddleName(vendor.getMiddleName());
        response.setLastName(vendor.getLastName());
        response.setEmail(vendor.getEmail());
        response.setLocation(vendor.getLocation());
        response.setRating(vendor.getRating());
        response.setCommission(vendor.getCommission());
        response.setContactNumber(vendor.getContactNumber());
        response.setCreatedDate(vendor.getCreatedDate());
        response.setUpdatedDate(vendor.getUpdatedDate());
        response.setStatus(vendor.isStatus());
        return response;
    }
}