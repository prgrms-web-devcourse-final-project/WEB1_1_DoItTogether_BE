package com.doittogether.platform.presentation;

import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.presentation.dto.OAuth.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class UserContoller {

    @RequestMapping("/api/v1/user/update")
    @Operation(summary = "닉네임 변경", description = "사용자의 닉네임을 변경합니다.")
    public ResponseEntity<SuccessResponse<Void>> updateUserName(@AuthenticationPrincipal CustomOAuth2User user) {
        System.out.println("닉네임 변경 : " + user.getName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess());
    }

    @PostMapping("/api/auth/logout")
    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    public ResponseEntity<SuccessResponse<Void>> logout() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess());
    }
}
