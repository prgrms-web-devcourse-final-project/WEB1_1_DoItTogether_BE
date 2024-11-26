package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
public class UserChannel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userGroupId;

    private Role role;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;
}
