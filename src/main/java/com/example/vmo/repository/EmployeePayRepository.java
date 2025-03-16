package com.example.vmo.repository;

import com.example.vmo.model.EmployeePay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeePayRepository extends JpaRepository<EmployeePay, Long> {

    /**
     * Find all employee pay records where status is true
     * 
     * @return List of active employee pay records
     */
    List<EmployeePay> findByStatusTrue();

    /**
     * Find all employee pay records for a specific employee where status is true
     * 
     * @param employeeId The employee ID to search for
     * @return List of active employee pay records for the given employee
     */
    List<EmployeePay> findByEmployeeIdAndStatusTrue(Long employeeId);

    /**
     * Find all employee pay records for a specific employee ordered by effective
     * date descending
     * 
     * @param employeeId The employee ID to search for
     * @return List of employee pay records for the given employee
     */
    List<EmployeePay> findByEmployeeIdOrderByEffectiveDateDesc(Long employeeId);

    /**
     * Find all active employee pay records for a specific employee ordered by
     * effective date descending
     * 
     * @param employeeId The employee ID to search for
     * @return List of active employee pay records for the given employee
     */
    List<EmployeePay> findByEmployeeIdAndStatusTrueOrderByEffectiveDateDesc(Long employeeId);
}