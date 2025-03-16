package com.example.vmo.repository;

import com.example.vmo.model.StatementOfWorkPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementOfWorkPositionRepository extends JpaRepository<StatementOfWorkPosition, Long> {

    /**
     * Find all statement of work position records where status is true
     * 
     * @return List of active statement of work position records
     */
    List<StatementOfWorkPosition> findByStatusTrue();

    /**
     * Find all StatementOfWorkPositions by sowId where status is true
     * 
     * @param sowId The statement of work ID
     * @return List of active StatementOfWorkPositions for the given SOW
     */
    List<StatementOfWorkPosition> findBySowIdAndStatusTrue(Long sowId);

    /**
     * Find all StatementOfWorkPositions by positionId where status is true
     * 
     * @param positionId The position ID
     * @return List of active StatementOfWorkPositions for the given position
     */
    List<StatementOfWorkPosition> findByPositionIdAndStatusTrue(Long positionId);
}