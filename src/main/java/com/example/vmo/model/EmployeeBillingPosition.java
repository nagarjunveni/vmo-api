package com.example.vmo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_billing_position")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBillingPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee employee;

    @Column(name = "statement_of_work_position_id", nullable = false)
    private Long statementOfWorkPositionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statement_of_work_position_id", referencedColumnName = "id", insertable = false, updatable = false)
    private StatementOfWorkPosition statementOfWorkPosition;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @Column(name = "billed_by", nullable = false)
    private Long billedBy;

    @Column(name = "billed_date", nullable = false)
    private LocalDateTime billedDate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();

        if (billedDate == null) {
            billedDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}