package com.example.vmo.model;

import com.example.vmo.enums.PositionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statement_of_work_positions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOfWorkPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sow_id", nullable = false)
    private Long sowId;

    @Column(name = "position_id", nullable = false)
    private Long positionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PositionType type;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sow_id", referencedColumnName = "id", insertable = false, updatable = false)
    private StatementOfWork statementOfWork;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Position position;
}