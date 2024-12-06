package com.doittogether.platform.business.preset;

import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.preset.request.PresetCategoryRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.response.*;
import org.springframework.data.domain.Pageable;

public interface PresetService {
    void addDefaultCategoriesToChannel(Channel channel);
    PresetKeywordListResponse getFlatPresetList(User user, Long channelId, Pageable pageable);
    CategoryPresetResponse getPresetsByCategory(User user, Long channelId, Long presetCategoryId, Pageable pageable);
    CategoryListResponse getAllCategories(User user, Long channelId, Pageable pageable);
    CategoryPresetListResponse getAllCategoriesWithItems(User user, Long channelId, Pageable pageable);
    PresetCategoryRegisterResponse createPresetCategory(User user, Long channelId, PresetCategoryRegisterRequest request);
    PresetItemRegisterResponse createPreset(User user, Long channelId, Long presetCategoryId, PresetItemRegisterRequest request);
    PresetCategoryDeleteResponse deletePresetCategory(User user, Long channelId, Long presetCategoryId);
    PresetItemDeleteResponse deletePresetDetail(User user, Long channelId, Long presetCategoryId, Long presetItemId);
}
