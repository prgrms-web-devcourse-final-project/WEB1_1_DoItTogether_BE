package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "모든 카테고리 이름 응답")
@Builder
public record CategoryListResponse(
        @Schema(description = "카테고리 이름 리스트", example = "[\"거실\", \"침실\", \"부엌\"]")
        List<CategoryResponse> categoryList
) {
    public static CategoryListResponse of(List<CategoryResponse> categoryList) {
        return CategoryListResponse.builder()
                .categoryList(categoryList)
                .build();
    }
}
