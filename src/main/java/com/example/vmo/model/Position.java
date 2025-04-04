package com.example.vmo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.vmo.enums.PositionType;

@Entity
@Table(name = "position")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "hourly_rate", nullable = false, precision = 19, scale = 2)
    private BigDecimal hourlyRate;

    @Column(name = "monthly_rate", nullable = false, precision = 19, scale = 2)
    private BigDecimal monthlyRate;

    @Column(name = "skills", nullable = false)
    private String skills;

    @Column(name = "expertise", nullable = false)
    private String expertise;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PositionType type;

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