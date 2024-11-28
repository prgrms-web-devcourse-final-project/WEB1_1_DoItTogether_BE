package com.doittogether.platform.presentation;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.channel.ChannelService;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.channel.request.ChannelKickUserRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelRegisterRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelUpdateRequest;
import com.doittogether.platform.presentation.dto.channel.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/channels")
@RequiredArgsConstructor
@Tag(name = "채널 API", description = "채널 관리 API")
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("/my")
    @Operation(summary = "나의 채널 목록 조회", description = "내가 속한 채널 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<ChannelListResponse>> getChannel(
            @AuthenticationPrincipal User user,
            @ParameterObject Pageable pageable) {

        if(user.getEmail() == null) // 임시 로그인
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        channelService.getMyChannels(user.getEmail(), pageable)
                ));
    }

    @PostMapping
    @Operation(summary = "채널 생성", description = "관리자 유저가 새로운 채널을 생성합니다.")
    public ResponseEntity<SuccessResponse<ChannelRegisterResponse>> createChannel(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid ChannelRegisterRequest request) {

        if(user.getEmail() == null) // 임시 로그인
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        channelService.createChannel(user.getEmail(), request)
                ));
    }

    @PutMapping("/{channelId}/name")
    @Operation(summary = "채널명 변경", description = "관리자 유저가 채널명을 변경합니다.")
    public ResponseEntity<SuccessResponse<ChannelUpdateResponse>> updateChannelName(
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId, @RequestBody @Valid ChannelUpdateRequest request) {

        if(user.getEmail() == null) // 임시 로그인
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        channelService.updateChannelName(user.getEmail(), channelId, request)
                ));
    }

    @GetMapping("/{channelId}/users")
    @Operation(summary = "채널 사용자 조회", description = "채널에 포함된 모든 사용자를 조회합니다.")
    public ResponseEntity<SuccessResponse<ChannelUserListResponse>> getChannelUsers(
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId,
            @ParameterObject Pageable pageable) {

        if(user.getEmail() == null) // 임시 로그인
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        channelService.getChannelUsers(user.getEmail(), channelId, pageable)
                ));
    }

    @PostMapping("/{channelId}/invite-link")
    @Operation(summary = "초대 링크 생성", description = "특정 채널에 대한 초대 링크를 생성합니다.")
    public ResponseEntity<SuccessResponse<ChannelInviteLinkResponse>> generateInviteLink(
            @PathVariable("channelId") Long channelId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        channelService.generateInviteLink(channelId)
                ));
    }

    @PostMapping("/join/{inviteLink}")
    @Operation(summary = "초대 링크로 방 입장", description = "초대 링크를 통해 채널에 입장합니다.")
    public ResponseEntity<SuccessResponse<ChannelJoinResponse>> joinChannelViaInviteLink(
            @AuthenticationPrincipal User user,
            @PathVariable("inviteLink") String inviteLink) {

        if(user.getEmail() == null) // 임시 로그인
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        channelService.joinChannelViaInviteLink(user.getEmail(), inviteLink)
                ));
    }

    @PostMapping("/{channelId}/kick")
    @Operation(summary = "특정 유저 추방", description = "특정 유저를 채널에서 강퇴합니다.")
    public ResponseEntity<SuccessResponse<ChannelKickUserResponse>> kickUserFromChannel(
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId, @Valid @RequestBody ChannelKickUserRequest request) {

        if(user.getEmail() == null) // 임시 로그인
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        channelService.kickUserFromChannel(user.getEmail(), channelId, request)
                ));
    }

    @GetMapping("/{channelId}/housework")
    @Operation(summary = "집안일 목록 조회", description = "일자별 집안일 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<ChannelHouseworkListResponse>> getHouseworkByDate(
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // yyyy-MM-dd 형식을 지원
            @Parameter(description = "선택 날짜 (yyyy-MM-dd 형식)", example = "2024-11-25") LocalDate targetDate,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        channelService.getHouseworkByDate(channelId, targetDate, pageable)
                ));
    }
}
