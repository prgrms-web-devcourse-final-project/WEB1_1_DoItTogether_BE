package com.doittogether.platform.presentation.dto.preset.request;

import com.doittogether.platform.domain.entity.PresetCategory;
import com.doittogether.platform.domain.entity.PresetItem;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프리셋 아이템 등록 요청")
public record PresetItemRegisterRequest(
        @Schema(description = "프리셋 아이템 이름", example = "[\"바닥 청소\", \"유리창 닦기\"]")
        String item
) {
    public static PresetItem toEntity(PresetItemRegisterRequest request,
                                      PresetCategory category) {
        return PresetItem.of(request.item, category);
    }
}
