package com.doittogether.platform.domain.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long groupId;

    private String name;

    @OneToMany(mappedBy = "group", cascade = ALL)
    private List<UserGroup> userGroups;

    @OneToMany(mappedBy = "group", cascade = ALL)
    private List<PresetItem> presetItems;
}
