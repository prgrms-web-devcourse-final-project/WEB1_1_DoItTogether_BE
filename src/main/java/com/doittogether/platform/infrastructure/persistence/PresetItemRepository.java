package com.doittogether.platform.infrastructure.persistence;

import com.doittogether.platform.domain.entity.PresetItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresetItemRepository extends JpaRepository<PresetItem, Long> {
}
