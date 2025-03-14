package com.example.vmo.enums;

public enum StatementOfWorkType {
    FIXED_BID("Fixed Bid"),
    TIME_AND_MATERIALS("Time and Materials");

    private final String displayName;

    StatementOfWorkType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}