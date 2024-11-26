package com.doittogether.platform.presentation;

import com.doittogether.platform.application.global.ApiResponse;
import com.doittogether.platform.presentation.dto.preset.request.PresetDeleteRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemRegisterRequest;
import com.doittogether.platform.presentation.dto.preset.request.PresetItemUpdateRequest;
import com.doittogether.platform.presentation.dto.preset.response.PresetDeleteResponse;
import com.doittogether.platform.presentation.dto.preset.response.PresetItemDeleteResponse;
import com.doittogether.platform.presentation.dto.preset.response.PresetItemRegisterResponse;
import com.doittogether.platform.presentation.dto.preset.response.PresetItemUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/presets")
@Tag(name = "프리셋 아이템 API", description = "프리셋 아이템 관리 API")
public class PresetItemController {

    @PostMapping("/{channelId}")
    @Operation(summary = "프리셋 아이템 생성", description = "지정된 채널 ID에 새로운 프리셋 아이템을 생성합니다.")
    public ResponseEntity<ApiResponse<PresetItemRegisterResponse>> createPreset(
            @PathVariable Long channelId, @RequestBody PresetItemRegisterRequest request
    ) {

        return ApiResponse.onSuccess(null);
    }

    @PutMapping("/{channelId}")
    @Operation(summary = "프리셋 아이템 수정", description = "지정된 채널 ID의 기존 프리셋 아이템을 수정합니다.")
    public ResponseEntity<ApiResponse<PresetItemUpdateResponse>> updatePreset(
            @PathVariable Long channelId, @RequestBody PresetItemUpdateRequest request
    ) {

        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{channelId}/{presetItemId}")
    @Operation(summary = "프리셋 아이템 삭제", description = "지정된 채널 ID 및 프리셋 아이템 ID를 기반으로 프리셋 아이템을 삭제합니다.")
    public ResponseEntity<ApiResponse<PresetItemDeleteResponse>> deletePreset(
            @PathVariable Long channelId, @PathVariable Long presetItemId
    ) {

        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{channelId}/{presetItemId}/details")
    @Operation(summary = "프리셋 세부 항목 삭제",
            description = "지정된 채널 ID 및 프리셋 아이템 ID의 세부 항목을 삭제합니다. 삭제할 세부 항목은 요청 본문에서 제공합니다.")
    public ResponseEntity<ApiResponse<PresetDeleteResponse>> deletePresetDetail(
            @PathVariable Long channelId, @PathVariable Long presetItemId,
            @RequestBody PresetDeleteRequest request
    ) {
        return ApiResponse.onSuccess(null);
    }

}
