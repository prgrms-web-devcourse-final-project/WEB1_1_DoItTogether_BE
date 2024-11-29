package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;

    private String nickName;

    private String email;

    @OneToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Personality> personalities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<UserChannel> userChannels = new ArrayList<>();

    public static User of(String nickName, String email, ProfileImage profileImage) {
        User user = new User();
        user.nickName = nickName;
        user.email = email;
        user.profileImage = profileImage;
        return user;
    }
}
