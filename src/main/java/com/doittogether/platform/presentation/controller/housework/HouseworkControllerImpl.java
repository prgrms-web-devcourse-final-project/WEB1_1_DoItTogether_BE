package com.doittogether.platform.presentation.controller.housework;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.ExceptionResponse;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.housework.HouseworkService;
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
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channels/{channelId}/houseworks")
public class HouseworkControllerImpl implements HouseworkController {

    private final HouseworkService houseworkService;


    @GetMapping("/{targetDate}/{pageNumber}/{pageSize}")
    @Operation(summary = "집안일 목록 조회", description = "일자별 집안일 목록을 조회합니다.")
    @Override
    public ResponseEntity<SuccessResponse<HouseworkSliceResponse>> findHouseworksByDate(
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "선택 날짜 (yyyy-MM-dd 형식)", example = "2024-11-25") LocalDate targetDate,
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "선택 날짜 (yyyy-MM-dd 형식)", example = "2024-11-25") LocalDate targetDate,
            @RequestParam("assigneeId") Long assigneeId,
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
    public ResponseEntity<SuccessResponse<HouseworkResponse>> findHouseworkByHouseworkId(
            @AuthenticationPrincipal User user,
            @PathVariable("houseworkId") Long houseworkId){
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.onSuccess(SuccessCode._OK,
                        houseworkService.findHouseworkByHouseworkId(user, houseworkId)));
    }

    @PostMapping
    @Operation(summary = "집안일 추가", description = "집안일 카테고리, 작업, 담당자를 설정하여 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "추가 성공")
    })
    @Override
    public ResponseEntity<SuccessResponse<Void>> addHousework(@AuthenticationPrincipal User user,
                                                              @PathVariable("channelId") Long channelId,
                                                              @RequestBody HouseworkRequest request) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId,
            @PathVariable(name = "houseworkId") Long houseworkId,
            @RequestBody @Valid HouseworkRequest request) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        houseworkService.updateHousework(loginUser, houseworkId, channelId, request);
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
    public ResponseEntity<SuccessResponse<Void>> deleteHousework(@AuthenticationPrincipal User user,
                                                                 @PathVariable("channelId") Long channelId,
                                                                 @PathVariable(name = "houseworkId") Long houseworkId) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        houseworkService.deleteHousework(loginUser, houseworkId, channelId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._NO_CONTENT));
    }
}