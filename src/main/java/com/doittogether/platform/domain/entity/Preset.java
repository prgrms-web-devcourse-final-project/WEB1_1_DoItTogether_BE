package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "preset")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preset extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long presetId;

    private String value;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "preset_item_id", nullable = true)
    private PresetItem presetItem;
}
