package com.doittogether.platform.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public UserChannel(Role role, User user, Channel channel) {
        this.role = role;
        this.user = user;
        this.channel = channel;
    }

    public static UserChannel of(User user, Channel channel, Role role) {
        return UserChannel.builder()
                .user(user)
                .channel(channel)
                .role(role)
                .build();
    }
}
