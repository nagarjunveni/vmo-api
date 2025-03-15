package com.example.vmo.repository;

import com.example.vmo.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    /**
     * Find all positions where status is true
     * 
     * @return List of active positions
     */
    List<Position> findByStatusTrue();
}