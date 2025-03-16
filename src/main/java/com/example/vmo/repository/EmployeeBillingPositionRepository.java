package com.example.vmo.repository;

import com.example.vmo.model.EmployeeBillingPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeBillingPositionRepository extends JpaRepository<EmployeeBillingPosition, Long> {

    /**
     * Find all employee billing position records where status is true
     * 
     * @return List of active employee billing position records
     */
    List<EmployeeBillingPosition> findByStatusTrue();

    /**
     * Find all employee billing position records for a specific employee where
     * status is true
     * 
     * @param employeeId The employee ID to search for
     * @return List of active employee billing position records for the given
     *         employee
     */
    List<EmployeeBillingPosition> findByEmployeeIdAndStatusTrue(Long employeeId);

    /**
     * Find all employee billing position records for a specific statement of work
     * position where status is true
     * 
     * @param statementOfWorkPositionId The statement of work position ID to search
     *                                  for
     * @return List of active employee billing position records for the given
     *         statement of work position
     */
    List<EmployeeBillingPosition> findByStatementOfWorkPositionIdAndStatusTrue(Long statementOfWorkPositionId);
}