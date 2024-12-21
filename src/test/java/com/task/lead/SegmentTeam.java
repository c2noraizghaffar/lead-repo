package com.task.lead;

public enum SegmentTeam {

    MICRO("micro"),
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");

    private final String value;

    SegmentTeam(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
