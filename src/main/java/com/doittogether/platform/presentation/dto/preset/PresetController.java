package com.doittogether.platform.presentation.dto.preset;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.preset.PresetService;
import com.doittogether.platform.presentation.dto.preset.request.PresetCategoryRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channels/{channelId}/presets")
@RequiredArgsConstructor
@Tag(name = "프리셋 API", description = "프리셋 관리 API")
public class PresetController {

    private final PresetService presetService;

    @GetMapping("/keywords")
    @Operation(summary = "전체 프리셋 키워드(카테고리-아이템) 리스트 조회",
            description = "모든 프리셋 데이터를 카테고리와 값을 분리하여 페이지네이션 형태로 반환합니다.")
    public ResponseEntity<SuccessResponse<PresetKeywordListResponse>> getFlatPresetList(
            @PathVariable("channelId") Long channelId,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, presetService.getFlatPresetList(channelId, pageable)));
    }

    @GetMapping("/categories/{presetCategoryId}/items")
    @Operation(summary = "특정 프리셋 카테고리의 프리셋 아이템 리스트 조회",
            description = "지정된 카테고리의 프리셋 데이터를 페이지네이션 형태로 반환합니다.")
    public ResponseEntity<SuccessResponse<CategoryPresetResponse>> getPresetsByCategory(
            @PathVariable("presetCategoryId") Long presetCategoryId,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, presetService.getPresetsByCategory(presetCategoryId, pageable)));
    }

    @GetMapping("/categories/names")
    @Operation(summary = "모든 프리셋 카테고리 이름 조회", description = "모든 카테고리 이름을 페이지네이션 형태로 반환합니다.")
    public ResponseEntity<SuccessResponse<CategoryListResponse>> getAllCategories(
            @PathVariable("channelId") Long channelId,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, presetService.getAllCategories(channelId, pageable)));
    }

    @GetMapping("/categories/items")
    @Operation(summary = "모든 카테고리와 해당 프리셋 아이템 리스트 조회",
            description = "모든 카테고리와 각 카테고리에 속한 프리셋 아이템 리스트를 반환합니다.")
    public ResponseEntity<SuccessResponse<CategoryPresetListResponse>> getAllCategoriesWithItems(
            @PathVariable("channelId") Long channelId,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,
                        presetService.getAllCategoriesWithItems(channelId, pageable)));
    }

    @PostMapping("/categories")
    @Operation(summary = "프리셋 카테고리 생성", description = "지정된 채널 ID에 새로운 프리셋 카테고리를 생성합니다.")
    public ResponseEntity<SuccessResponse<PresetCategoryRegisterResponse>> createPresetCategory(
            @PathVariable("channelId") Long channelId,
            @RequestBody PresetCategoryRegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.onSuccess(SuccessCode._CREATED, presetService.createPresetCategory(channelId, request)));
    }

    @PostMapping("/categories/{presetCategoryId}/items")
    @Operation(summary = "프리셋 아이템 생성", description = "지정된 채널 ID와 프리셋 카테고리 ID에 새로운 프리셋 아이템을 생성합니다.")
    public ResponseEntity<SuccessResponse<PresetItemRegisterResponse>> createPreset(
            @PathVariable("presetCategoryId") Long presetCategoryId,
            @RequestBody PresetItemRegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.onSuccess(SuccessCode._CREATED, presetService.createPreset(presetCategoryId, request)));
    }

    @DeleteMapping("/categories/{presetCategoryId}")
    @Operation(summary = "프리셋 카테고리 삭제",
            description = "지정된 채널 ID 내에서 프리셋 카테고리를 삭제합니다. 프리셋 카테고리에 포함된 리스트 프리셋도 함께 삭제됩니다.")
    public ResponseEntity<SuccessResponse<PresetCategoryDeleteResponse>> deletePreset(
            @PathVariable("presetCategoryId") Long presetCategoryId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, presetService.deletePresetCategory(presetCategoryId)));
    }

    @DeleteMapping("/items/{presetItemId}")
    @Operation(summary = "프리셋 세부 항목 삭제",
            description = "지정된 채널 ID와 프리셋 카테고리 ID 내에서 특정 프리셋 세부 항목(리스트 프리셋)을 삭제합니다.")
    public ResponseEntity<SuccessResponse<PresetItemDeleteResponse>> deletePresetDetail(
            @PathVariable("presetItemId") Long presetItemId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, presetService.deletePresetDetail(presetItemId)));
    }
}
