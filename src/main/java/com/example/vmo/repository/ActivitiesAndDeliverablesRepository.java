package com.example.vmo.repository;

import com.example.vmo.model.ActivitiesAndDeliverables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivitiesAndDeliverablesRepository extends JpaRepository<ActivitiesAndDeliverables, Long> {

    /**
     * Find all activities and deliverables where status is true
     * 
     * @return List of active activities and deliverables
     */
    List<ActivitiesAndDeliverables> findByStatusTrue();

    /**
     * Find all activities and deliverables for a specific SOW where status is true
     * 
     * @param sowId The SOW ID to search for
     * @return List of active activities and deliverables for the given SOW
     */
    List<ActivitiesAndDeliverables> findBySowIdAndStatusTrue(Long sowId);
}