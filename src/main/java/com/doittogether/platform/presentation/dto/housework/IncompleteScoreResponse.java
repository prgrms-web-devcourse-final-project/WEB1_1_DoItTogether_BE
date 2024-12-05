package com.doittogether.platform.presentation.dto.housework;

import lombok.Builder;

import java.util.List;

@Builder
public record IncompleteScoreResponse (
        List<PersonalIncompleteScoreResponse> incompleteScoreResponses
){
    public static IncompleteScoreResponse of (List<PersonalIncompleteScoreResponse> dailyHousework){
        return IncompleteScoreResponse.builder()
                .incompleteScoreResponses(dailyHousework)
                .build();
    }
}
