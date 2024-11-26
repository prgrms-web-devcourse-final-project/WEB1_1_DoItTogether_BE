package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
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
@NoArgsConstructor
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

    @Builder
    public User(String nickName, String email, ProfileImage profileImage) {
        this.nickName = nickName;
        this.email = email;
        this.profileImage = profileImage;
    }
}
