package com.task.lead;

public enum SegmentType {

    GOV("gov"),
    NON_PROFIT("non_profit"),
    ENTERPRISE("enterprise"),
    MULTINATIONAL("multinational"),
    TECH("tech"),
    FINTECH("fintech"),
    FREELANCER("freelancer");

    private final String value;

    SegmentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
