package com.doittogether.platform.business.preset;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.preset.PresetException;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.PresetCategory;
import com.doittogether.platform.domain.entity.PresetItem;
import com.doittogether.platform.infrastructure.persistence.ChannelRepository;
import com.doittogether.platform.infrastructure.persistence.PresetCategoryRepository;
import com.doittogether.platform.infrastructure.persistence.PresetItemRepository;
import com.doittogether.platform.presentation.dto.preset.request.PresetCategoryRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PresetServiceImpl implements PresetService {

    private final ChannelRepository channelRepository;
    private final PresetCategoryRepository presetCategoryRepository;
    private final PresetItemRepository presetItemRepository;

    @Override
    public PresetKeywordListResponse getFlatPresetList(Long channelId, Pageable pageable) {
        channelRepository.findById(channelId)
                .orElseThrow(() -> new PresetException(ExceptionCode.CHANNEL_NOT_FOUND));

        Page<PresetItem> items = presetItemRepository.findAllByChannelId(channelId, pageable);

        List<PresetKeywordResponse> presetKeywordList = items.stream()
                .map(item -> PresetKeywordResponse.builder()
                        .presetCategoryId(item.getPresetCategory().getPresetCategoryId())
                        .category(item.getPresetCategory().getCategory())
                        .presetId(item.getPresetItemId())
                        .value(item.getValue())
                        .build())
                .toList();

        return PresetKeywordListResponse.of(presetKeywordList);
    }

    @Override
    public CategoryPresetResponse getPresetsByCategory(Long presetCategoryId, Pageable pageable) {
        PresetCategory category = presetCategoryRepository.findById(presetCategoryId)
                .orElseThrow(() -> new PresetException(ExceptionCode.PRESET_CATEGORY_NOT_FOUND));

        List<PresetItem> items = presetItemRepository.findAllByPresetCategoryId(presetCategoryId, pageable);

        List<PresetItemResponse> presetList = items.stream()
                .map(item -> PresetItemResponse.builder()
                        .presetItemId(item.getPresetItemId())
                        .value(item.getValue())
                        .build())
                .toList();

        return CategoryPresetResponse.builder()
                .presetCategoryId(category.getPresetCategoryId())
                .category(category.getCategory())
                .presetList(presetList)
                .build();
    }

    @Override
    public CategoryListResponse getAllCategories(Long channelId, Pageable pageable) {
        channelRepository.findById(channelId)
                .orElseThrow(() -> new PresetException(ExceptionCode.CHANNEL_NOT_FOUND));

        List<PresetCategory> categories = presetCategoryRepository.findAllByChannelId(channelId, pageable);

        List<CategoryResponse> categoryList = categories.stream()
                .map(category -> CategoryResponse.builder()
                        .presetCategoryId(category.getPresetCategoryId())
                        .category(category.getCategory())
                        .build())
                .toList();

        return CategoryListResponse.of(categoryList);
    }

    @Override
    public PresetCategoryRegisterResponse createPresetCategory(Long channelId, PresetCategoryRegisterRequest request) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new PresetException(ExceptionCode.CHANNEL_NOT_FOUND));

        PresetCategory category = PresetCategoryRegisterRequest.toEntity(request, channel);
        category = presetCategoryRepository.save(category);

        return PresetCategoryRegisterResponse.from(category);
    }

    @Override
    public PresetItemRegisterResponse createPreset(Long presetCategoryId, PresetItemRegisterRequest request) {
        PresetCategory category = presetCategoryRepository.findById(presetCategoryId)
                .orElseThrow(() -> new PresetException(ExceptionCode.PRESET_CATEGORY_NOT_FOUND));

        PresetItem item = PresetItemRegisterRequest.toEntity(request, category);
        item = presetItemRepository.save(item);

        return PresetItemRegisterResponse.of(category, item);
    }

    @Override
    public PresetCategoryDeleteResponse deletePresetCategory(Long presetCategoryId) {
        PresetCategory category = presetCategoryRepository.findById(presetCategoryId)
                .orElseThrow(() -> new PresetException(ExceptionCode.PRESET_CATEGORY_NOT_FOUND));
        presetCategoryRepository.delete(category);
        return PresetCategoryDeleteResponse.of(presetCategoryId);
    }

    @Override
    public PresetItemDeleteResponse deletePresetDetail(Long presetItemId) {
        PresetItem item = presetItemRepository.findById(presetItemId)
                .orElseThrow(() -> new PresetException(ExceptionCode.PRESET_ITEM_NOT_FOUND));
        presetItemRepository.delete(item);
        return new PresetItemDeleteResponse(presetItemId);
    }
}
