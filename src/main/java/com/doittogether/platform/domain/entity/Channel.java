package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "channel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long channelId;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<UserChannel> userChannels;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Housework> houseworks;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<PresetItem> presetItems;

    @Builder
    public Channel(String name) {
        this.name = name;
    }
}
