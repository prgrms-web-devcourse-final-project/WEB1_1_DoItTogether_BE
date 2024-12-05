package com.doittogether.platform.presentation.controller.reaction;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.reaction.ReactionService;
import com.doittogether.platform.business.user.UserService;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.enumeration.ReactionType;
import com.doittogether.platform.presentation.dto.reaction.ReactionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/channels/{channelId}/reactions")
@RequiredArgsConstructor
@Tag(name = "알림 관리 API", description = "사용자 간 칭찬 및 찔림 관리 API")
public class ReactionController {

    private final ReactionService reactionService;
    private final UserService userService;

    @PostMapping("/poke")
    @Operation(summary = "찌르기 요청", description = "특정 사용자를 찌르는 요청을 처리합니다.")
    public ResponseEntity<SuccessResponse<Void>> pokeUser(
            Principal principal,
            @PathVariable("channelId") Long channelId,
            @RequestBody @Valid ReactionRequest request
    ) {
        Long userId = Long.parseLong(principal.getName());
        User user = userService.findByIdOrThrow(userId);

        reactionService.react(user, channelId, request, ReactionType.POKE);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(SuccessCode._OK)
        );
    }

    @PostMapping("/compliment")
    @Operation(summary = "칭찬하기 요청", description = "특정 사용자를 칭찬하는 요청을 처리합니다.")
    public ResponseEntity<SuccessResponse<Void>> complimentUser(
            Principal principal,
            @PathVariable("channelId") Long channelId,
            @RequestBody @Valid ReactionRequest request
    ) {
        Long userId = Long.parseLong(principal.getName());
        User user = userService.findByIdOrThrow(userId);

        reactionService.react(user, channelId, request, ReactionType.COMPLIMENT);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(SuccessCode._OK)
        );
    }
}
