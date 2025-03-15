package com.example.vmo.repository;

import com.example.vmo.model.StatementOfWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatementOfWorkRepository extends JpaRepository<StatementOfWork, Long> {

    /**
     * Find all statements of work where status is true
     * 
     * @return List of active statements of work
     */
    List<StatementOfWork> findByStatusTrue();

    /**
     * Find statement of work by custom ID
     * 
     * @param statementOfWorkId The custom ID to search for
     * @return Optional containing the statement of work if found
     */
    Optional<StatementOfWork> findByStatementOfWorkId(String statementOfWorkId);

    /**
     * Find statements of work by name containing the search term and status is true
     * 
     * @param name The name to search for
     * @return List of statements of work with the specified name
     */
    List<StatementOfWork> findByNameContainingAndStatusTrue(String name);

    /**
     * Find the highest sequence number for a given year
     * 
     * @param year The year to search for
     * @return The highest sequence number for the given year
     */
    @Query("SELECT MAX(CAST(SUBSTRING(s.statementOfWorkId, 9) AS int)) FROM StatementOfWork s WHERE s.statementOfWorkId LIKE :prefix%")
    Integer findHighestSequenceNumberForYear(@Param("prefix") String prefix);

    /**
     * Count statements of work by custom ID starting with the given prefix
     * 
     * @param prefix The prefix to search for
     * @return The count of statements of work with the given prefix
     */
    int countByStatementOfWorkIdStartingWith(String prefix);
}