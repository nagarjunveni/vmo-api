package com.example.vmo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities_and_deliverables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesAndDeliverables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phase", nullable = false)
    private String phase;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "deliverable", length = 1000)
    private String deliverable;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "sow_id", nullable = false)
    private Long sowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sow_id", referencedColumnName = "id", insertable = false, updatable = false)
    private StatementOfWork statementOfWork;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

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