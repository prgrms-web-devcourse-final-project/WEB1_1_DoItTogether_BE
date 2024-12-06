package com.doittogether.platform.business.preset;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.preset.PresetException;
import com.doittogether.platform.business.channel.ChannelValidator;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.PresetCategory;
import com.doittogether.platform.domain.entity.PresetItem;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.preset.PresetCategoryRepository;
import com.doittogether.platform.infrastructure.persistence.preset.PresetItemRepository;
import com.doittogether.platform.infrastructure.persistence.channel.ChannelRepository;
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

    private final ChannelValidator channelValidator;

    @Override
    public void addDefaultCategoriesToChannel(Channel channel) {
        List<String> defaultCategories = List.of("거실", "침실", "주방", "욕실", "기타");

        defaultCategories.forEach(categoryName -> {
            PresetCategory presetCategory = PresetCategory.of(categoryName, channel);
            presetCategoryRepository.save(presetCategory);
        });
    }

    @Override
    public PresetKeywordListResponse getFlatPresetList(User user, Long channelId, Pageable pageable) {
        channelValidator.checkChannelParticipation(user, channelId);

        Page<PresetItem> items = presetItemRepository.findAllByChannelId(channelId, pageable);

        List<PresetKeywordResponse> presetKeywordList = items.stream()
                .map(item -> PresetKeywordResponse.builder()
                        .presetCategoryId(item.getPresetCategory().getPresetCategoryId())
                        .category(item.getPresetCategory().getCategory())
                        .presetId(item.getPresetItemId())
                        .name(item.getName())
                        .build())
                .toList();

        return PresetKeywordListResponse.of(presetKeywordList);
    }

    @Override
    public CategoryPresetResponse getPresetsByCategory(User user, Long channelId, Long presetCategoryId, Pageable pageable) {
        channelValidator.checkChannelParticipation(user, channelId);

        PresetCategory category = presetCategoryRepository.findById(presetCategoryId)
                .orElseThrow(() -> new PresetException(ExceptionCode.PRESET_CATEGORY_NOT_FOUND));

        List<PresetItem> items = presetItemRepository.findAllByPresetCategoryId(presetCategoryId, pageable);

        List<PresetItemResponse> presetItemList = items.stream()
                .map(item -> PresetItemResponse.builder()
                        .presetItemId(item.getPresetItemId())
                        .name(item.getName())
                        .build())
                .toList();

        return CategoryPresetResponse.builder()
                .presetCategoryId(category.getPresetCategoryId())
                .category(category.getCategory())
                .presetItemList(presetItemList)
                .build();
    }

    @Override
    public CategoryListResponse getAllCategories(User user, Long channelId, Pageable pageable) {
        channelValidator.checkChannelParticipation(user, channelId);

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
    public CategoryPresetListResponse getAllCategoriesWithItems(User user, Long channelId, Pageable pageable) {
        channelValidator.checkChannelParticipation(user, channelId);

        List<PresetCategory> categories = presetCategoryRepository.findAllWithItemsByChannelId(channelId, pageable);

        List<CategoryPresetResponse> categoryPresetList = categories.stream()
                .map(category -> {
                    List<PresetItemResponse> presetItemList = category.getPresetItems().stream()
                            .map(item -> PresetItemResponse.builder()
                                    .presetItemId(item.getPresetItemId())
                                    .name(item.getName())
                                    .build())
                            .toList();

                    return CategoryPresetResponse.builder()
                            .presetCategoryId(category.getPresetCategoryId())
                            .category(category.getCategory())
                            .presetItemList(presetItemList)
                            .build();
                })
                .toList();

        return CategoryPresetListResponse.of(categoryPresetList);
    }

    @Override
    public PresetCategoryRegisterResponse createPresetCategory(User user, Long channelId, PresetCategoryRegisterRequest request) {
        Channel channel = channelValidator.checkChannelParticipationAndGetChannel(user, channelId);

        PresetCategory category = PresetCategoryRegisterRequest.toEntity(request, channel);
        category = presetCategoryRepository.save(category);

        return PresetCategoryRegisterResponse.from(category);
    }

    @Override
    public PresetItemRegisterResponse createPreset(User user, Long channelId, Long presetCategoryId, PresetItemRegisterRequest request) {
        channelValidator.checkChannelParticipation(user, channelId);

        PresetCategory category = presetCategoryRepository.findById(presetCategoryId)
                .orElseThrow(() -> new PresetException(ExceptionCode.PRESET_CATEGORY_NOT_FOUND));

        PresetItem item = PresetItemRegisterRequest.toEntity(request, category);
        item = presetItemRepository.save(item);

        return PresetItemRegisterResponse.of(category, item);
    }

    @Override
    public PresetCategoryDeleteResponse deletePresetCategory(User user, Long channelId, Long presetCategoryId) {
        channelValidator.checkChannelParticipation(user, channelId);

        PresetCategory category = presetCategoryRepository.findById(presetCategoryId)
                .orElseThrow(() -> new PresetException(ExceptionCode.PRESET_CATEGORY_NOT_FOUND));
        presetCategoryRepository.delete(category);
        return PresetCategoryDeleteResponse.of(presetCategoryId);
    }

    @Override
    public PresetItemDeleteResponse deletePresetDetail(User user, Long presetCategoryId, Long channelId, Long presetItemId) {
        channelValidator.checkChannelParticipation(user, channelId);

        presetCategoryRepository.findById(presetCategoryId)
                .orElseThrow(() -> new PresetException(ExceptionCode.PRESET_CATEGORY_NOT_FOUND));

        PresetItem item = presetItemRepository.findById(presetItemId)
                .orElseThrow(() -> new PresetException(ExceptionCode.PRESET_ITEM_NOT_FOUND));
        presetItemRepository.delete(item);
        return new PresetItemDeleteResponse(presetItemId);
    }
}
