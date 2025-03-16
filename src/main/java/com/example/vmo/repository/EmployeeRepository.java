package com.example.vmo.repository;

import com.example.vmo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find all employees where status is true
     * 
     * @return List of active employees
     */
    List<Employee> findByStatusTrue();

    /**
     * Find employee by email where status is true
     * 
     * @param email The email to search for
     * @return Optional containing the employee if found
     */
    Optional<Employee> findByEmailAndStatusTrue(String email);

    /**
     * Find employees by vendorId where status is true
     * 
     * @param vendorId The vendor ID to search for
     * @return List of active employees for the given vendor
     */
    List<Employee> findByVendorIdAndStatusTrue(Long vendorId);

    /**
     * Check if an employee exists with the given email
     * 
     * @param email The email to check
     * @return true if employee exists, false otherwise
     */
    boolean existsByEmail(String email);
}