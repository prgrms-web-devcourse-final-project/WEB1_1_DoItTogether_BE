package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

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

    public static UserChannel of(User user, Channel channel, Role role) {
        UserChannel userChannel = new UserChannel();

        userChannel.user = user;
        userChannel.channel = channel;
        userChannel.role = role;
        return userChannel;
    }
}
