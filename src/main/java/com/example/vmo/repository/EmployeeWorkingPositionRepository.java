package com.example.vmo.repository;

import com.example.vmo.model.EmployeeWorkingPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeWorkingPositionRepository extends JpaRepository<EmployeeWorkingPosition, Long> {

    /**
     * Find all employee working position records where status is true
     * 
     * @return List of active employee working position records
     */
    List<EmployeeWorkingPosition> findByStatusTrue();

    /**
     * Find all employee working position records for a specific employee where
     * status is true
     * 
     * @param employeeId The employee ID to search for
     * @return List of active employee working position records for the given
     *         employee
     */
    List<EmployeeWorkingPosition> findByEmployeeIdAndStatusTrue(Long employeeId);

    /**
     * Find all employee working position records for a specific statement of work
     * position where status is true
     * 
     * @param statementOfWorkPositionId The statement of work position ID to search
     *                                  for
     * @return List of active employee working position records for the given
     *         statement of work position
     */
    List<EmployeeWorkingPosition> findByStatementOfWorkPositionIdAndStatusTrue(Long statementOfWorkPositionId);
}