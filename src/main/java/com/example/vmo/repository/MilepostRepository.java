package com.example.vmo.repository;

import com.example.vmo.model.Milepost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilepostRepository extends JpaRepository<Milepost, Long> {

    /**
     * Find all mileposts where status is true
     * 
     * @return List of active mileposts
     */
    List<Milepost> findByStatusTrue();

    /**
     * Find all mileposts for a specific SOW where status is true
     * 
     * @param sowId The SOW ID to search for
     * @return List of active mileposts for the given SOW
     */
    List<Milepost> findBySowIdAndStatusTrue(Long sowId);
}