package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
public class Personality extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long personalityId;

    private String value;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Personality(String value) {
        this.value = value;
    }
}
