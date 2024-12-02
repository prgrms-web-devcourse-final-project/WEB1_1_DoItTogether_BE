package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long profileImageId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String url;

    public static ProfileImage from(String url) {
        return new ProfileImage(url);
    }

    @Builder
    public ProfileImage(String url) {
        this.url = url;
    }
}
