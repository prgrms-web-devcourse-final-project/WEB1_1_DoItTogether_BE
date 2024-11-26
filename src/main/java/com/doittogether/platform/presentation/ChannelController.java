package com.doittogether.platform.presentation;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.BaseResponse;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.presentation.dto.channel.request.ChannelJoinRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelKickUserRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelRegisterRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelUpdateRequest;
import com.doittogether.platform.presentation.dto.channel.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channels")
@Tag(name = "채널 API", description = "채널 관리 API")
public class ChannelController {

    @PostMapping
    @Operation(summary = "채널 생성", description = "관리자 유저가 새로운 채널을 생성합니다.")
    public ResponseEntity<BaseResponse<ChannelRegisterResponse>> createChannel(
            @RequestBody @Valid ChannelRegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.onSuccess(SuccessCode._OK, null));
    }

    @PutMapping("/{channelId}/name")
    @Operation(summary = "채널명 변경", description = "관리자 유저가 채널명을 변경합니다.")
    public ResponseEntity<BaseResponse<Void>> updateChannelName(
            @PathVariable Long channelId, @RequestBody @Valid ChannelUpdateRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess());
    }

    @GetMapping("/{channelId}/users")
    @Operation(summary = "채널 사용자 조회", description = "채널에 포함된 모든 사용자를 조회합니다.")
    public ResponseEntity<BaseResponse<ChannelUserListResponse>> getChannelUsers(
            @PathVariable Long channelId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,null));
    }

    @PostMapping("/{channelId}/invite-link")
    @Operation(summary = "초대 링크 생성", description = "특정 채널에 대한 초대 링크를 생성합니다.")
    public ResponseEntity<BaseResponse<ChannelInviteLinkResponse>> generateInviteLink(
            @PathVariable Long channelId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,null));
    }

    @PostMapping("/{channelId}/kick")
    @Operation(summary = "특정 유저 추방", description = "특정 유저를 채널에서 강퇴합니다.")
    public ResponseEntity<BaseResponse<Void>> kickUserFromChannel(
            @PathVariable Long channelId, @Valid @RequestBody ChannelKickUserRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,null));
    }

    @PostMapping("/join")
    @Operation(summary = "초대 링크로 방 입장", description = "초대 링크를 통해 채널에 입장합니다.")
    public ResponseEntity<BaseResponse<ChannelJoinResponse>> joinChannelViaInviteLink(
            @Valid @RequestBody ChannelJoinRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,null));
    }

    @GetMapping("/{channelId}/housework")
    @Operation(summary = "집안일 목록 조회", description = "일자별 집안일 목록을 조회합니다.")
    public ResponseEntity<BaseResponse<ChannelHouseworkListResponse>> getHouseworkByDate(
            @PathVariable Long channelId,
            @RequestParam
            @Parameter(description = "선택 날짜 (yyyy-MM-dd 형식)", example = "2024-11-25")
            @NotBlank(message = "선택 날짜는 필수 입력 값입니다.")
            @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식은 yyyy-MM-dd여야 합니다.")
            String targetDate) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,null));
    }
}
