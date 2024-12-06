package com.doittogether.platform.presentation.controller.user;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.user.UserService;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.user.request.UserUpdateRequest;
import com.doittogether.platform.presentation.dto.user.response.UserResponse;
import com.doittogether.platform.presentation.dto.user.response.UserUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원 관리 API")
public class UserController {

    private final UserService userService;

    @GetMapping("/my")
    @Operation(summary = "나의 회원 정보 조회", description = "나의 회원 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<UserResponse>> getCurrentUser(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userService.findByIdOrThrow(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        UserResponse.from(user)
                ));
    }

    @PatchMapping("/my/profile")
    @Operation(summary = "나의 프로필 수정", description = "나의 프로필 정보를 수정합니다.")
    public ResponseEntity<SuccessResponse<UserUpdateResponse>> updateNickname(
            Principal principal,
            @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        Long userId = Long.parseLong(principal.getName());
        User user = userService.findByIdOrThrow(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        userService.updateNickname(user, userUpdateRequest)
                ));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "특정 회원 정보 조회", description = "특정 회원의 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<UserResponse>> getUserById(@PathVariable("userId") Long userId) {
        User user = userService.findByIdOrThrow(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        UserResponse.from(user)
                )
        );
    }

    @GetMapping("/my/profile/setup")
    @Operation(summary = "나의 초기 설정 상태 여부 조회", description = "나의 초기 설정 설정 여부를 조회합니다.")
    public ResponseEntity<SuccessResponse<Boolean>> isProfileComplete(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userService.findByIdOrThrow(userId);

        boolean isComplete = userService.isSetup(user);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(SuccessCode._OK, isComplete)
        );
    }

    @PatchMapping("/my/profile/setup")
    @Operation(summary = "나의 초기 설정 상태 수정", description = "나의 초기 설정 상태를 '완료'로 변경합니다.")
    public ResponseEntity<SuccessResponse<Void>> completeProfile(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userService.findByIdOrThrow(userId);

        userService.completeSetup(user);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(SuccessCode._OK)
        );
    }

    @DeleteMapping("/my")
    @Operation(summary = "회원 탈퇴", description = "현재 회원의 계정을 삭제합니다.")
    public ResponseEntity<SuccessResponse<Void>> deleteAccount(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(SuccessCode._NO_CONTENT)
        );
    }
}
