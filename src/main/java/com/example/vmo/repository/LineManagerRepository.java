package com.example.vmo.repository;

import com.example.vmo.model.LineManager;
import com.example.vmo.enums.LineManagerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineManagerRepository extends JpaRepository<LineManager, Long> {

    /**
     * Find all line managers where status is true
     * 
     * @return List of active line managers
     */
    List<LineManager> findByStatusTrue();

    /**
     * Find line managers by type
     * 
     * @param type The type to search for
     * @return List of line managers with the specified type
     */
    List<LineManager> findByTypeAndStatusTrue(LineManagerType type);

    /**
     * Find line managers by department
     * 
     * @param department The department to search for
     * @return List of line managers in the specified department
     */
    List<LineManager> findByDepartmentAndStatusTrue(String department);
}