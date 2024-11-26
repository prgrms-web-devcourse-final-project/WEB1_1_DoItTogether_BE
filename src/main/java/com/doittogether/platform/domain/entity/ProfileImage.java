package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class ProfileImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long profileImageId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String url;

    @Builder
    public ProfileImage(String url) {
        this.url = url;
    }
}
