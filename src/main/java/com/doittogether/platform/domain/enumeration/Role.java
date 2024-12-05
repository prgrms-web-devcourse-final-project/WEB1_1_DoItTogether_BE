package com.doittogether.platform.domain.enumeration;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("관리자"),
    PARTICIPANT("참가자");

    private final String description;

    Role(String description) {
        this.description = description;
    };

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
