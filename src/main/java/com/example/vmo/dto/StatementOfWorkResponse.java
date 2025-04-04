package com.example.vmo.dto;

import com.example.vmo.enums.ProjectState;
import com.example.vmo.enums.StatementOfWorkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOfWorkResponse {
    private Long id;
    private String statementOfWorkId; // Custom format: SOW-YYYY-XXX
    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private StatementOfWorkType type;
    private String typeDisplayName;
    private BigDecimal fixedBidAmount;
    private ProjectState projectState;
    private String projectStateDisplayName;

    private String projectScope;
    private String teamsAndConditions;
    private String assumptionsAndDependencies;

    private LineManagerResponse lineManager;
    private LineManagerResponse csxEscalationManager;
    private LineManagerResponse compnovaEscalationManager;
    private AuthorizedSignatureResponse authorizedSignature;

    private boolean status;

    // Position counts
    private int onsiteCount;
    private int offshoreCount;

    // List of positions
    private List<StatementOfWorkPositionResponse> positions;

    private List<ActivitiesAndDeliverablesResponse> activities;
    private List<MilepostResponse> mileposts;
}