package com.example.vmo.enums;

public enum LineManagerType {
    CSX_LINE_MANAGER("CSX Line Manager"),
    CSX_ESCALATION_MANAGER("CSX Escalation Manager"),
    COMPNOVA_ESCALATION_MANAGER("Compnova Escalation Manager");

    private final String displayName;

    LineManagerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}