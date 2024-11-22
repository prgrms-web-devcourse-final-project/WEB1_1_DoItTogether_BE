package com.doittogether.platform.presentation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "해당 리스폰스에 대한 설명")
public record SwaggerResponse(
        @Schema(description = "필드설명", example = "팔드값 예시")
        String name
) {
}