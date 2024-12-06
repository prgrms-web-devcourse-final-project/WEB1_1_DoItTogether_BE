package com.doittogether.platform.presentation.controller.personality;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.personality.PersonalityService;
import com.doittogether.platform.business.user.UserService;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.personality.PersonalityRequestDto;
import com.doittogether.platform.presentation.dto.personality.PersonalityResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/personalities")
@RequiredArgsConstructor
@Tag(name = "선호도조사 API", description = "성향 관리 API")
public class PersonalityController {

    private final PersonalityService personalityService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "사용자 성향 분석(GPT API)",
            description = "설문조사 내용을 분석하여 키워드를 반환합니다.")
    public ResponseEntity<SuccessResponse<PersonalityResponseDTO>> findKeywordsFromGPT(
            Principal principal, @RequestBody PersonalityRequestDto request) {
        Long userId = Long.parseLong(principal.getName());
        User loginUser = userService.findByIdOrThrow(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,
                        personalityService.findKeywordsFromGPT(loginUser, request)));
    }

    @GetMapping
    @Operation(summary = "사용자 성향 조회",
            description = "로그인된 사용자의 성향 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<PersonalityResponseDTO>> getUserPersonalities(
            Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User loginUser = userService.findByIdOrThrow(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,
                        personalityService.getUserPersonalities(loginUser)));
    }
}
