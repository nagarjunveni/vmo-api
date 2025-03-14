package com.example.vmo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.vmo.enums.ProjectState;
import com.example.vmo.enums.StatementOfWorkType;

@Entity
@Table(name = "statement_of_work")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOfWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "statement_of_work_id", nullable = false, unique = true)
    private String statementOfWorkId; // Custom format: SOW-YYYY-XXX

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatementOfWorkType type;

    @Column(name = "fixed_bid_amount", precision = 19, scale = 2)
    private BigDecimal fixedBidAmount;

    @Column(name = "project_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectState projectState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_manager_id", nullable = false)
    private LineManager lineManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escalation_manager_id", nullable = false)
    private LineManager escalationManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_escalation_manager_id", nullable = false)
    private LineManager vendorEscalationManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorized_signature_id", nullable = false)
    private AuthorizedSignature authorizedSignature;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}