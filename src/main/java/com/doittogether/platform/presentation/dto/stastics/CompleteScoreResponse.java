package com.doittogether.platform.presentation.dto.stastics;

import lombok.Builder;

import java.util.List;

@Builder
public record CompleteScoreResponse(
        List<PersonalCompleteScoreResponse> completeScoreSortedResponse
) {

    public static CompleteScoreResponse of (List<PersonalCompleteScoreResponse> weeklyStatistics){
        return CompleteScoreResponse.builder()
                .completeScoreSortedResponse(weeklyStatistics)
                .build();
    }
}
