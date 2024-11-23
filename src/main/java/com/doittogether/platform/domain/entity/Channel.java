package com.doittogether.platform.domain.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Table(name = "channel")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long channelId;

    private String name;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<UserChannel> userChannels;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Housework> houseworks;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<PresetItem> presetItems;
}
