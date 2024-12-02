package com.doittogether.platform.business;

import com.doittogether.platform.business.preset.PresetServiceImpl;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.PresetCategory;
import com.doittogether.platform.domain.entity.PresetItem;
import com.doittogether.platform.infrastructure.persistence.PresetCategoryRepository;
import com.doittogether.platform.infrastructure.persistence.PresetItemRepository;
import com.doittogether.platform.infrastructure.persistence.channel.ChannelRepository;
import com.doittogether.platform.presentation.dto.preset.request.PresetCategoryRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.response.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PresetServiceTest {
    @Mock private ChannelRepository channelRepository;

    @Mock private PresetCategoryRepository presetCategoryRepository;

    @Mock private PresetItemRepository presetItemRepository;

    @InjectMocks private PresetServiceImpl presetService;

    @Test
    void 전체_프리셋_키워드_리스트_조회() {
        Long validChannelId = 1L;

        Channel channel = Channel.of("Test Channel");
        setField(channel, "channelId", validChannelId);

        PresetCategory category = PresetCategory.of("Test Category", channel);
        setField(category, "presetCategoryId", 10L);

        PresetItem item1 = PresetItem.of("Item 1", category);
        setField(item1, "presetItemId", 100L);

        PresetItem item2 = PresetItem.of("Item 2", category);
        setField(item2, "presetItemId", 200L);

        Page<PresetItem> page = new PageImpl<>(List.of(item1, item2));
        lenient().when(channelRepository.findById(validChannelId)).thenReturn(Optional.of(channel));
        lenient().when(presetItemRepository.findAllByChannelId(validChannelId, PageRequest.of(0, 10))).thenReturn(page);

        PresetKeywordListResponse response = presetService.getFlatPresetList(validChannelId, PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(2, response.presetKeywordList().size());

        PresetKeywordResponse keyword1 = response.presetKeywordList().get(0);
        assertEquals(100L, keyword1.presetId());
        assertEquals("Test Category", keyword1.category());
        assertEquals("Item 1", keyword1.name());

        PresetKeywordResponse keyword2 = response.presetKeywordList().get(1);
        assertEquals(200L, keyword2.presetId());
        assertEquals("Test Category", keyword2.category());
        assertEquals("Item 2", keyword2.name());

        verify(channelRepository).findById(validChannelId);
        verify(presetItemRepository).findAllByChannelId(validChannelId, PageRequest.of(0, 10));
    }

    @Test
    void 특정_프리셋_카테고리의_아이템_리스트_조회() {
        Long presetCategoryId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        PresetCategory category = PresetCategory.of("거실", null);
        setField(category, "presetCategoryId", presetCategoryId);

        PresetItem item1 = PresetItem.of("바닥 청소", category);
        setField(item1, "presetItemId", 100L);

        PresetItem item2 = PresetItem.of("책상 정리", category);
        setField(item2, "presetItemId", 200L);

        List<PresetItem> items = List.of(item1, item2);

        lenient().when(presetCategoryRepository.findById(presetCategoryId)).thenReturn(Optional.of(category));
        lenient().when(presetItemRepository.findAllByPresetCategoryId(presetCategoryId, pageable)).thenReturn(items);

        CategoryPresetResponse response = presetService.getPresetsByCategory(presetCategoryId, pageable);

        assertNotNull(response);
        assertEquals(presetCategoryId, response.presetCategoryId());
        assertEquals("거실", response.category());
        assertEquals(2, response.presetItemList().size());

        PresetItemResponse preset1 = response.presetItemList().get(0);
        assertEquals(100L, preset1.presetItemId());
        assertEquals("바닥 청소", preset1.name());

        PresetItemResponse preset2 = response.presetItemList().get(1);
        assertEquals(200L, preset2.presetItemId());
        assertEquals("책상 정리", preset2.name());

        verify(presetCategoryRepository).findById(presetCategoryId);
        verify(presetItemRepository).findAllByPresetCategoryId(presetCategoryId, pageable);
    }

    @Test
    void 모든_프리셋_카테고리_이름_조회() {
        Long channelId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        Channel channel = Channel.of("Test Channel");
        setField(channel, "channelId", channelId);

        PresetCategory category1 = PresetCategory.of("거실", channel);
        setField(category1, "presetCategoryId", 100L);

        PresetCategory category2 = PresetCategory.of("침실", channel);
        setField(category2, "presetCategoryId", 200L);

        List<PresetCategory> categories = List.of(category1, category2);

        lenient().when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));
        lenient().when(presetCategoryRepository.findAllByChannelId(channelId, pageable)).thenReturn(categories);

        CategoryListResponse response = presetService.getAllCategories(channelId, pageable);

        assertNotNull(response);
        assertEquals(2, response.categoryList().size());

        CategoryResponse categoryResponse1 = response.categoryList().get(0);
        assertEquals(100L, categoryResponse1.presetCategoryId());
        assertEquals("거실", categoryResponse1.category());

        CategoryResponse categoryResponse2 = response.categoryList().get(1);
        assertEquals(200L, categoryResponse2.presetCategoryId());
        assertEquals("침실", categoryResponse2.category());

        verify(channelRepository).findById(channelId);
        verify(presetCategoryRepository).findAllByChannelId(channelId, pageable);
    }

    @Test
    void 프리셋_카테고리_생성() {
        Long channelId = 1L;
        String categoryName = "거실";

        Channel channel = Channel.of("Test Channel");
        setField(channel, "channelId", channelId);

        PresetCategoryRegisterRequest request = PresetCategoryRegisterRequest.builder()
                .category(categoryName)
                .build();

        PresetCategory category = PresetCategory.of(categoryName, channel);
        setField(category, "presetCategoryId", 100L);

        lenient().when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));
        lenient().when(presetCategoryRepository.save(any(PresetCategory.class))).thenReturn(category);

        PresetCategoryRegisterResponse response = presetService.createPresetCategory(channelId, request);

        assertNotNull(response);
        assertEquals(100L, response.presetCategoryId());
        assertEquals(categoryName, response.category());

        verify(channelRepository).findById(channelId);
        verify(presetCategoryRepository).save(any(PresetCategory.class));
    }

    @Test
    void 프리셋_아이템_생성() {
        Long presetCategoryId = 1L;
        String presetValue = "청소";

        PresetCategory category = PresetCategory.of("거실", null);
        setField(category, "presetCategoryId", presetCategoryId);

        PresetItemRegisterRequest request = PresetItemRegisterRequest.builder()
                .name(presetValue)
                .build();

        PresetItem item = PresetItem.of(presetValue, category);
        setField(item, "presetItemId", 100L);

        lenient().when(presetCategoryRepository.findById(presetCategoryId)).thenReturn(Optional.of(category));
        lenient().when(presetItemRepository.save(any(PresetItem.class))).thenReturn(item);

        PresetItemRegisterResponse response = presetService.createPreset(presetCategoryId, request);

        assertNotNull(response);
        assertEquals(presetCategoryId, response.presetCategoryId());
        assertEquals(100L, response.presetItemId());
        assertEquals(presetValue, response.name());

        verify(presetCategoryRepository).findById(presetCategoryId);
        verify(presetItemRepository).save(any(PresetItem.class));
    }

    @Test
    void 프리셋_카테고리_삭제() {
        Long presetCategoryId = 1L;

        PresetCategory category = PresetCategory.of("거실", null);
        setField(category, "presetCategoryId", presetCategoryId);

        when(presetCategoryRepository.findById(presetCategoryId)).thenReturn(Optional.of(category));

        PresetCategoryDeleteResponse response = presetService.deletePresetCategory(presetCategoryId);

        assertNotNull(response);
        assertEquals(presetCategoryId, response.presetCategoryId());

        verify(presetCategoryRepository).findById(presetCategoryId);
        verify(presetCategoryRepository).delete(category);
    }
    
    @Test
    void 프리셋_아이템_삭제() {
        Long presetItemId = 1L;

        PresetItem item = PresetItem.of("청소", null);
        setField(item, "presetItemId", presetItemId);

        when(presetItemRepository.findById(presetItemId)).thenReturn(Optional.of(item));

        PresetItemDeleteResponse response = presetService.deletePresetDetail(presetItemId);

        assertNotNull(response);
        assertEquals(presetItemId, response.presetItemId());

        verify(presetItemRepository).findById(presetItemId);
        verify(presetItemRepository).delete(item);
    }

    // id 값 설정할 수 있도록 임시
    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
