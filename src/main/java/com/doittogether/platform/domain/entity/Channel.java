package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "channel")
@NoArgsConstructor
public class Channel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long channelId;

    private String name;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<UserChannel> userChannels;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<PresetItem> presetItems;
}
