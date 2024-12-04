package com.doittogether.platform.infrastructure.persistence.preset;

import com.doittogether.platform.domain.entity.PresetItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PresetItemRepository extends JpaRepository<PresetItem, Long> {
    @Query("SELECT i FROM PresetItem i WHERE i.presetCategory.channel.channelId = :channelId")
    Page<PresetItem> findAllByChannelId(@Param("channelId") Long channelId, Pageable pageable);

    @Query("SELECT i FROM PresetItem i WHERE i.presetCategory.presetCategoryId = :presetCategoryId")
    List<PresetItem> findAllByPresetCategoryId(@Param("presetCategoryId") Long presetCategoryId, Pageable pageable);

}
