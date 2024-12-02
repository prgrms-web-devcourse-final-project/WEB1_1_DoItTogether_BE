package com.doittogether.platform.presentation.dto.stastics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MonthlyMVPResponse(

        @NotNull
        @Schema(description = "칭찬 MVP", example = "이서준")
        String complimentMVPNickName,

        @NotNull
        @Schema(description = "찌르기 MVP", example = "이서준")
        String pokeMVPNickName
) {
}
