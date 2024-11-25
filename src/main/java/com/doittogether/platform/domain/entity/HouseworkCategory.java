package com.doittogether.platform.domain.entity;

public enum HouseworkCategory {
    LIVING_ROOM("거실"),
    KITCHEN("주방"),
    BATHROOM("욕실"),
    OTHER("기타");

    private final String displayName;

    HouseworkCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
