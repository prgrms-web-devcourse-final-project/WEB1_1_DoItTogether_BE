package com.doittogether.platform.domain.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Personality extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long personalityId;

    private String value;

    public Personality(String value) {
        this.value = value;
    }
}
