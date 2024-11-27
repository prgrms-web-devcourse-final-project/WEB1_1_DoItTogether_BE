package com.doittogether.platform.presentation;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.presentation.dto.preset.request.PresetDeleteRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemUpdateRequest;
import com.doittogether.platform.presentation.dto.preset.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/v1/presets/{channelId}")
@Tag(name = "프리셋 아이템 API", description = "프리셋 아이템 관리 API")
public class PresetItemController {

    @GetMapping("/flat-list")
    @Operation(summary = "전체 프리셋 키워드 리스트 조회", description = "모든 프리셋 데이터를 카테고리와 값을 분리하여 페이지네이션 형태로 반환합니다.")
    public ResponseEntity<SuccessResponse<FlatPresetResponse>> getFlatPresetList(
            @PathVariable("channelId") Long channelId,
            @ParameterObject @Schema(description = "페이지네이션 정보") Pageable pageable
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @GetMapping("/category/{presetItemId}")
    @Operation(summary = "특정 카테고리의 프리셋 리스트 조회", description = "지정된 카테고리의 프리셋 데이터를 페이지네이션 형태로 반환합니다.")
    public ResponseEntity<SuccessResponse<CategoryPresetResponse>> getPresetsByCategory(
            @PathVariable("channelId") Long channelId,
            @PathVariable("presetItemId") String presetItemId,
            @ParameterObject @Schema(description = "페이지네이션 정보") Pageable pageable
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @GetMapping("/categories")
    @Operation(summary = "모든 카테고리 이름 조회", description = "중복 없이 모든 카테고리 이름을 페이지네이션 형태로 반환합니다.")
    public ResponseEntity<SuccessResponse<AllCategoriesResponse>> getAllCategories(
            @PathVariable("channelId") Long channelId,
            @ParameterObject @Schema(description = "페이지네이션 정보") Pageable pageable
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }



    @PostMapping
    @Operation(summary = "프리셋 아이템 생성", description = "지정된 채널 ID에 새로운 프리셋 아이템을 생성합니다.")
    public ResponseEntity<SuccessResponse<PresetItemRegisterResponse>> createPreset(
            @PathVariable("channelId") Long channelId, @RequestBody PresetItemRegisterRequest request
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @PutMapping
    @Operation(summary = "프리셋 아이템 수정", description = "지정된 채널 ID의 기존 프리셋 아이템을 수정합니다.")
    public ResponseEntity<SuccessResponse<PresetItemUpdateResponse>> updatePreset(
            @PathVariable("channelId") Long channelId, @RequestBody PresetItemUpdateRequest request
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @DeleteMapping("/{presetItemId}")
    @Operation(summary = "프리셋 아이템 삭제", description = "지정된 채널 ID 및 프리셋 아이템 ID를 기반으로 프리셋 아이템을 삭제합니다.")
    public ResponseEntity<SuccessResponse<PresetItemDeleteResponse>> deletePreset(
            @PathVariable("channelId") Long channelId, @PathVariable Long presetItemId
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @DeleteMapping("/{presetItemId}/details")
    @Operation(summary = "프리셋 세부 항목 삭제",
            description = "지정된 채널 ID 및 프리셋 아이템 ID의 세부 항목을 삭제합니다. 삭제할 세부 항목은 요청 본문에서 제공합니다.")
    public ResponseEntity<SuccessResponse<PresetDeleteResponse>> deletePresetDetail(
            @PathVariable("channelId") Long channelId, @PathVariable Long presetItemId,
            @RequestBody PresetDeleteRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

}
