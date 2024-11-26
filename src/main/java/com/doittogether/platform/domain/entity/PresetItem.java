package com.doittogether.platform.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "preset_item")
@NoArgsConstructor
public class PresetItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long presetId;

    private String value;

    private String category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;
}
