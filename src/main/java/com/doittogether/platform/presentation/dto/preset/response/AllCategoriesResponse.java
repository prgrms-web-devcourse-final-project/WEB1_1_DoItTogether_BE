package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "모든 카테고리 이름 응답")
@Builder
public record AllCategoriesResponse(
        @Schema(description = "카테고리 이름 리스트", example = "[\"거실\", \"침실\", \"부엌\"]")
        List<String> categories
) {
    public static AllCategoriesResponse of(List<String> categories) {
        return AllCategoriesResponse.builder()
                .categories(categories)
                .build();
    }
}
