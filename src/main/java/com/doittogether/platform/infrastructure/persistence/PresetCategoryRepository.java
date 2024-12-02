package com.doittogether.platform.infrastructure.persistence;

import com.doittogether.platform.domain.entity.PresetCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PresetCategoryRepository extends JpaRepository<PresetCategory, Long> {
    @Query("SELECT c FROM PresetCategory c WHERE c.channel.channelId = :channelId")
    List<PresetCategory> findAllByChannelId(@Param("channelId") Long channelId, Pageable pageable);

    @Query("SELECT DISTINCT c FROM PresetCategory c LEFT JOIN FETCH c.presetItems WHERE c.channel.channelId = :channelId")
    List<PresetCategory> findAllWithItemsByChannelId(@Param("channelId") Long channelId, Pageable pageable);
}
