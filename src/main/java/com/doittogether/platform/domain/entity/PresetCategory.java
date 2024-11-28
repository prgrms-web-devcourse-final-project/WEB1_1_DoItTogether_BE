package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "preset_category")
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
}
