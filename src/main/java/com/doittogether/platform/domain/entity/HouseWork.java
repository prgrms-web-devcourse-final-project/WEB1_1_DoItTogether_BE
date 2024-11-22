package com.doittogether.platform.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HouseWork extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long houseWorkId;

    private LocalDateTime startDateTime;

    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}
