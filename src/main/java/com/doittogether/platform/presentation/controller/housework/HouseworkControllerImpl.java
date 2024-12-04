package com.doittogether.platform.presentation.controller.housework;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.ExceptionResponse;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.housework.HouseworkService;
import com.doittogether.platform.business.user.UserService;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import com.doittogether.platform.presentation.dto.housework.HouseworkResponse;
import com.doittogether.platform.presentation.dto.housework.HouseworkSliceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channels/{channelId}/houseworks")
@Tag(name = "집안일 API", description = "집안일 관리 API")
public class HouseworkControllerImpl implements
        HouseworkController {

    private final HouseworkService houseworkService;
    private final UserService userService;


    @GetMapping("/{targetDate}/{pageNumber}/{pageSize}")
    @Operation(summary = "집안일 목록 조회", description = "일자별 집안일 목록을 조회합니다.")
    @Override
    public ResponseEntity<SuccessResponse<HouseworkSliceResponse>> findHouseworksByDate(
            Principal principal,
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "선택 날짜 (yyyy-MM-dd 형식)", example = "2024-11-25") LocalDate targetDate,
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Long userId = Long.parseLong(principal.getName());
        User loginUser = userService.findByIdOrThrow(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        houseworkService.findAllByChannelIdAndTargetDate(loginUser, channelId, targetDate, pageable)
                ));
    }

    @GetMapping("/{targetDate}/{pageNumber}/{pageSize}/{assigneeId}")
    @Operation(summary = "집안일 담당자별 목록 조회", description = "일자별 담당자별 집안일 목록을 조회합니다.")
    @Override
    public ResponseEntity<SuccessResponse<HouseworkSliceResponse>> findHouseworksByDateAndAssignee(
            Principal principal,
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "선택 날짜 (yyyy-MM-dd 형식)", example = "2024-11-25") LocalDate targetDate,
            @RequestParam("assigneeId") Long assigneeId,
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Long userId = Long.parseLong(principal.getName());
        User user = userService.findByIdOrThrow(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        houseworkService.findAllByChannelIdAndTargetDateAndAssigneeId(channelId, targetDate,
                                assigneeId, pageable)
                ));
    }

    @GetMapping("/{houseworkId}")
    @Operation(summary = "집안일 Id별 상세 정보 조회", description = "집안일 Id별 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "수정 성공")
    })
    @Override
    public ResponseEntity<SuccessResponse<HouseworkResponse>> findHouseworkByChannelIdAndHouseworkId(
            Principal principal,
            @PathVariable("channelId") Long channelId,
            @PathVariable("houseworkId") Long houseworkId){
        Long userId = Long.parseLong(principal.getName());
        User user = userService.findByIdOrThrow(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,
                        houseworkService.findHouseworkByChannelIdAndHouseworkId(loginUser, channelId, houseworkId)));
    }

    @PostMapping
    @Operation(summary = "집안일 추가", description = "집안일 카테고리, 작업, 담당자를 설정하여 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "추가 성공")
    })
    @Override
    public ResponseEntity<SuccessResponse<Void>> addHousework(Principal principal,
                                                              @PathVariable("channelId") Long channelId,
                                                              @RequestBody HouseworkRequest request) {
        Long userId = Long.parseLong(principal.getName());
        User loginUser = userService.findByIdOrThrow(userId);
        houseworkService.addHousework(channelId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.onSuccess());
    }

    @PutMapping("/{houseworkId}")
    @Operation(summary = "집안일 수정", description = "기존 집안일의 카테고리, 작업, 담당자를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공", content =
            @Content(schema = @Schema(implementation = SuccessResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음", content =
            @Content(schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content =
            @Content(schema = @Schema(implementation = ExceptionResponse.class))
            )
    })
    @Override
    public ResponseEntity<SuccessResponse<Void>> updateHousework(
            Principal principal,
            @PathVariable("channelId") Long channelId,
            @PathVariable("houseworkId") Long houseworkId,
            @RequestBody @Valid HouseworkRequest request) {
        Long userId = Long.parseLong(principal.getName());
        User loginUser = userService.findByIdOrThrow(userId);
        houseworkService.updateHousework(loginUser, houseworkId, channelId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess());
    }


    @PutMapping("/{houseworkId}/changeStatus")
    @Operation(summary = "집안일 상태 변경", description = "기존 집안일 상태를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공", content =
            @Content(schema = @Schema(implementation = SuccessResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음", content =
            @Content(schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content =
            @Content(schema = @Schema(implementation = ExceptionResponse.class))
            )
    })
    @Override
    public ResponseEntity<SuccessResponse<Void>> changeStatus(
            Principal principal,
            @PathVariable("channelId") Long channelId,
            @PathVariable("houseworkId") Long houseworkId
    ) {
        Long userId = Long.parseLong(principal.getName());
        User loginUser = userService.findByIdOrThrow(userId);
        houseworkService.updateStatus(loginUser, channelId, houseworkId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess());
    }


    @DeleteMapping("/{houseworkId}")
    @Operation(summary = "집안일  삭제", description = "기존 집안일을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공", content =
            @Content(schema = @Schema(implementation = SuccessResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음", content =
            @Content(schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content =
            @Content(schema = @Schema(implementation = ExceptionResponse.class))
            )
    })
    @Override
    public ResponseEntity<SuccessResponse<Void>> deleteHousework(Principal principal,
                                                                 @PathVariable("channelId") Long channelId,
                                                                 @PathVariable(name = "houseworkId") Long houseworkId) {
        Long userId = Long.parseLong(principal.getName());
        User loginUser = userService.findByIdOrThrow(userId);
        houseworkService.deleteHousework(loginUser, houseworkId, channelId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._NO_CONTENT));
    }
}