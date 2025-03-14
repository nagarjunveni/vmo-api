package com.example.vmo.repository;

import com.example.vmo.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    /**
     * Find all vendors where status is true
     * 
     * @return List of active vendors
     */
    List<Vendor> findByStatusTrue();

    /**
     * Find vendor by employee identification number
     * 
     * @param employeeIdentificationNumber The employee identification number to
     *                                     search for
     * @return Optional containing the vendor if found
     */
    Optional<Vendor> findByEmployeeIdentificationNumber(String employeeIdentificationNumber);

    /**
     * Check if vendor exists with the given employee identification number
     * 
     * @param employeeIdentificationNumber The employee identification number to
     *                                     check
     * @return true if vendor exists, false otherwise
     */
    boolean existsByEmployeeIdentificationNumber(String employeeIdentificationNumber);

    /**
     * Find vendors by company name containing the search term and status is true
     * 
     * @param name The company name to search for
     * @return List of vendors with the specified company name
     */
    List<Vendor> findByCompanyNameContainingAndStatusTrue(String name);
}