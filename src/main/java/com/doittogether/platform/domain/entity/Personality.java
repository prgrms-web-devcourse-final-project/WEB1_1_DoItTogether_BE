package com.doittogether.platform.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.doittogether.platform.domain.enumeration.PersonalityStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Personality extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long personalityId;

    private String value;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PersonalityStatus status;

    public static Personality of(String value, User user, PersonalityStatus status) {
        Personality personality = new Personality();
        personality.value = value;
        personality.user = user;
        personality.status = status;
        return personality;
    }

    public String retrieveValue() {
        return value;
    }
}
