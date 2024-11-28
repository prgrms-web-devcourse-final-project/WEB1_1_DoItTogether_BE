package com.doittogether.platform.infrastructure.persistence;

import com.doittogether.platform.domain.entity.PresetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresetCategoryRepository extends JpaRepository<PresetCategory, Long> {
}
