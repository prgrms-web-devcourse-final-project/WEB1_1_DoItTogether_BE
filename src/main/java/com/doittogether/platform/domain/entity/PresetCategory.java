package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "preset_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PresetCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long presetCategoryId;

    @Column(nullable = false)
    private String category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "presetCategory", cascade = CascadeType.ALL)
    private List<PresetItem> presetItems;

    public static PresetCategory of(String category, Channel channel) {
        PresetCategory presetCategory = new PresetCategory();
        presetCategory.category = category;
        presetCategory.channel = channel;
        return presetCategory;
    }
}
