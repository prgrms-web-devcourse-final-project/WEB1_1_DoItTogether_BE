package com.doittogether.platform.presentation.controller.personality;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.personality.PersonalityService;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.personality.PersonalityRequestDto;
import com.doittogether.platform.presentation.dto.personality.PersonalityResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/personalities")
@RequiredArgsConstructor
@Tag(name = "선호도조사 API", description = "성향 관리 API")
public class PersonalityController {

    private final PersonalityService personalityService;

    @GetMapping
    @Operation(summary = "사용자 성향 조회",
            description = "설문조사 내용을 분석하여 키워드를 반환합니다.")
    public ResponseEntity<SuccessResponse<PersonalityResponseDTO>> findKeywordsFromGPT(
            @AuthenticationPrincipal User user, @RequestBody
    PersonalityRequestDto request) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,
                        personalityService.findKeywordsFromGPT(user, request)));
    }
}
