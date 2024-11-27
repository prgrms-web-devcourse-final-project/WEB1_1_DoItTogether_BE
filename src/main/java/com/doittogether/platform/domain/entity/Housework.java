package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Housework extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long houseworkId;

    private LocalDateTime startDateTime;

    private String task;

    private HouseworkCategory category;

    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User assignee;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    public boolean isAllocator(User user) {
        return this.assignee == user;
    }
}
