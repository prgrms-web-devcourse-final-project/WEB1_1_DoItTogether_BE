package com.doittogether.platform.presentation.dto.housework;

import static lombok.AccessLevel.PRIVATE;

import com.doittogether.platform.domain.entity.Housework;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.data.domain.Slice;

@Builder(access = PRIVATE)
public record HouseworkSliceResponse(
        List<HouseworkResponse> responses,
        int currentPage,
        int size,
        boolean first,
        boolean last
) {
    public static HouseworkSliceResponse from(final Slice<Housework> houseworks) {
        return HouseworkSliceResponse.builder()
                .responses(
                        houseworks.stream()
                                .map(HouseworkResponse::from)
                                .collect(Collectors.toUnmodifiableList())
                )
                .currentPage(houseworks.getNumber())
                .size(houseworks.getSize())
                .first(houseworks.isFirst())
                .last(houseworks.isLast())
                .build();
    }
}
