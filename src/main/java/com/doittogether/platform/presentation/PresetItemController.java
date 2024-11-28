package com.doittogether.platform.presentation;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.presentation.dto.preset.request.PresetCategoryRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channels/{channelId}/presets")
@Tag(name = "프리셋 아이템 API", description = "프리셋 아이템 관리 API")
public class PresetItemController {

    @GetMapping("/keywords")
    @Operation(summary = "전체 프리셋 키워드 리스트 조회", description = "모든 프리셋 데이터를 카테고리와 값을 분리하여 페이지네이션 형태로 반환합니다.")
    public ResponseEntity<SuccessResponse<PresetKeywordListResponse>> getFlatPresetList(
            @PathVariable("channelId") Long channelId,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @GetMapping("/categories/{presetCategoryId}")
    @Operation(summary = "특정 카테고리의 프리셋 리스트 조회", description = "지정된 카테고리의 프리셋 데이터를 페이지네이션 형태로 반환합니다.")
    public ResponseEntity<SuccessResponse<CategoryPresetResponse>> getPresetsByCategory(
            @PathVariable("channelId") Long channelId,
            @PathVariable("presetCategoryId") String presetCategoryId,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @GetMapping("/categories/names")
    @Operation(summary = "모든 카테고리 이름 조회", description = "중복 없이 모든 카테고리 이름을 페이지네이션 형태로 반환합니다.")
    public ResponseEntity<SuccessResponse<CategoryListResponse>> getAllCategories(
            @PathVariable("channelId") Long channelId,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @PostMapping
    @Operation(summary = "프리셋 카테고리 생성", description = "지정된 채널 ID에 새로운 프리셋 카테고리를 생성합니다.")
    public ResponseEntity<SuccessResponse<PresetCategoryRegisterResponse>> createPresetCategory(
            @PathVariable("channelId") Long channelId,
            @RequestBody PresetCategoryRegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.onSuccess(SuccessCode._CREATED, null));
    }

    @PostMapping("/{presetCategoryId}/details")
    @Operation(summary = "프리셋 생성", description = "지정된 채널 ID와 프리셋 카테고리 ID에 새로운 프리셋을 생성합니다.")
    public ResponseEntity<SuccessResponse<PresetItemRegisterResponse>> createPreset(
            @PathVariable("channelId") Long channelId,
            @PathVariable("presetCategoryId") Long presetCategoryId,
            @RequestBody PresetItemRegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.onSuccess(SuccessCode._CREATED, null));
    }

    @DeleteMapping("/{presetCategoryId}")
    @Operation(summary = "프리셋 카테고리 삭제",
            description = "지정된 채널 ID 내에서 프리셋 카테고리를 삭제합니다. 프리셋 카테고리에 포함된 리스트 프리셋도 함께 삭제됩니다.")
    public ResponseEntity<SuccessResponse<PresetCategoryDeleteResponse>> deletePreset(
            @PathVariable("channelId") Long channelId, @PathVariable("presetCategoryId") Long presetCategoryId
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @DeleteMapping("/{presetCategoryId}/details/{presetId}")
    @Operation(summary = "프리셋 세부 항목 삭제",
            description = "지정된 채널 ID와 프리셋 카테고리 ID 내에서 특정 프리셋 세부 항목(리스트 프리셋)을 삭제합니다.")
    public ResponseEntity<SuccessResponse<PresetItemDeleteResponse>> deletePresetDetail(
            @PathVariable("channelId") Long channelId, @PathVariable("presetCategoryId") Long presetCategoryId,
            @PathVariable("presetId") Long presetId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

}
