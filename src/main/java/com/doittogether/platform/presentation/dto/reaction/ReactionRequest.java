package com.doittogether.platform.presentation.dto.reaction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "알림 요청")
public record ReactionRequest (
        @NotNull
        @Schema(description = "타겟 사용자 ID", example = "1")
        Long targetUserId
) {
}
