package com.doittogether.platform.presentation.dto.stastics;

import java.util.List;

public record CompleteScoreResponse(
        List<PersonalCompleteScoreResponse> completeScoreSortedResponse
) {
}
