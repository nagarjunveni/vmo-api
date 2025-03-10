package com.example.vmo.repository;

import com.example.vmo.model.AuthorizedSignature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizedSignatureRepository extends JpaRepository<AuthorizedSignature, Long> {

    /**
     * Find all authorized signatures where status is true
     * 
     * @return List of active authorized signatures
     */
    List<AuthorizedSignature> findByStatusTrue();

    /**
     * Check if an email already exists
     * 
     * @param email The email to check
     * @return True if email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Check if an email already exists for a different signature (used during
     * update)
     * 
     * @param email The email to check
     * @param id    The id to exclude from the check
     * @return True if email exists for another signature, false otherwise
     */
    boolean existsByEmailAndIdNot(String email, Long id);
}