package com.doittogether.platform.domain.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;

    private String nickName;

    private String email;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private boolean isSetup = false;

    @OneToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Personality> personalities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<UserChannel> userChannels = new ArrayList<>();

    public static User of(String nickName, String email, String socialId, ProfileImage profileImage) {
        User user = new User();
        user.nickName = nickName;
        user.email = email;
        user.socialId = socialId;
        user.profileImage = profileImage;
        return user;
    }

    public Long retrieveUserId() {
        return userId;
    }

    public String retrieveNickName() {
        return nickName;
    }

    public String retrieveEmail() {
        return email;
    }

    public String retrieveSocialId() {
        return socialId;
    }

    public ProfileImage retrieveProfileImage() {
        return profileImage;
    }

    public boolean isSetup() {
        return isSetup;
    }

    public void completeSetup() {
        isSetup = true;
    }

    public void updateNickName(String newNickName) {
        nickName = newNickName;
    }
}
