package com.example.vmo.service;

import com.example.vmo.dto.VendorRequest;
import com.example.vmo.dto.VendorResponse;

import java.util.List;

public interface VendorService {

    /**
     * Create a new vendor
     * 
     * @param request The vendor request data
     * @return The created vendor response
     */
    VendorResponse createVendor(VendorRequest request);

    /**
     * Update an existing vendor
     * 
     * @param id      The id of the vendor to update
     * @param request The updated vendor data
     * @return The updated vendor response
     */
    VendorResponse updateVendor(Long id, VendorRequest request);

    /**
     * Get a vendor by id
     * 
     * @param id The id of the vendor to retrieve
     * @return The vendor response
     */
    VendorResponse getVendorById(Long id);

    /**
     * Get a vendor by employee identification number
     * 
     * @param employeeIdentificationNumber The employee identification number to
     *                                     search for
     * @return The vendor response
     */
    VendorResponse getVendorByEmployeeIdentificationNumber(String employeeIdentificationNumber);

    /**
     * Get vendors by name
     * 
     * @param name The name to search for
     * @return List of vendors with the specified name
     */
    List<VendorResponse> getVendorsByName(String name);

    /**
     * Get all active vendors
     * 
     * @return List of active vendor responses
     */
    List<VendorResponse> getAllActiveVendors();

    /**
     * Soft delete a vendor by setting status to false
     * 
     * @param id The id of the vendor to delete
     */
    void deleteVendor(Long id);
}