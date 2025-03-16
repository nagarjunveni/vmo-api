package com.example.vmo.repository;

import com.example.vmo.model.EmployeeBonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeBonusRepository extends JpaRepository<EmployeeBonus, Long> {

    /**
     * Find all employee bonus records where status is true
     * 
     * @return List of active employee bonus records
     */
    List<EmployeeBonus> findByStatusTrue();

    /**
     * Find all employee bonus records for a specific employee where status is true
     * 
     * @param employeeId The employee ID to search for
     * @return List of active employee bonus records for the given employee
     */
    List<EmployeeBonus> findByEmployeeIdAndStatusTrue(Long employeeId);

    /**
     * Find all employee bonus records for a specific employee ordered by effective
     * date descending
     * 
     * @param employeeId The employee ID to search for
     * @return List of employee bonus records for the given employee
     */
    List<EmployeeBonus> findByEmployeeIdOrderByEffectiveDateDesc(Long employeeId);

    /**
     * Find all active employee bonus records for a specific employee ordered by
     * effective date descending
     * 
     * @param employeeId The employee ID to search for
     * @return List of active employee bonus records for the given employee
     */
    List<EmployeeBonus> findByEmployeeIdAndStatusTrueOrderByEffectiveDateDesc(Long employeeId);
}