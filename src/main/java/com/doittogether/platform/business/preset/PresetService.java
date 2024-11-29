package com.doittogether.platform.business.preset;

import com.doittogether.platform.presentation.dto.preset.request.PresetCategoryRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.response.*;
import org.springframework.data.domain.Pageable;

public interface PresetService {
    PresetKeywordListResponse getFlatPresetList(Long channelId, Pageable pageable);
    CategoryPresetResponse getPresetsByCategory(Long presetCategoryId, Pageable pageable);
    CategoryListResponse getAllCategories(Long channelId, Pageable pageable);
    PresetCategoryRegisterResponse createPresetCategory(Long channelId, PresetCategoryRegisterRequest request);
    PresetItemRegisterResponse createPreset(Long presetCategoryId, PresetItemRegisterRequest request);
    PresetCategoryDeleteResponse deletePresetCategory(Long presetCategoryId);
    PresetItemDeleteResponse deletePresetDetail(Long presetItemId);
}
