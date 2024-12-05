package com.doittogether.platform.domain.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "channel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long channelId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<UserChannel> userChannels;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<Housework> houseworks;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<PresetCategory> presetCategories;

    public static Channel of(String name) {
        final Channel channel = new Channel();
        channel.name = name;
        return channel;
    }

    public Long retrieveChannelId() {
        return channelId;
    }

    public String retrieveName() {
        return name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
