package com.doittogether.platform.presentation.controller.housework;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.ExceptionResponse;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.housework.HouseworkServiceImpl;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/{groupId}/houseworks")
public class HouseworkController {

    private final HouseworkServiceImpl houseworkService;

    @PostMapping
    @Operation(summary = "집안일 추가", description = "집안일 카테고리, 작업, 담당자를 설정하여 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    public ResponseEntity<SuccessResponse<Void>> addHousework(@AuthenticationPrincipal User user,
                                                           @RequestBody HouseworkRequest request) {
        User target = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
    public ResponseEntity<SuccessResponse<Void>> updateHousework(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "houseworkId") Long id,
            @RequestBody @Valid HouseworkRequest request) {
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
    public ResponseEntity<SuccessResponse<Void>> deleteHousework(@AuthenticationPrincipal User user,
                                                              @PathVariable(name = "houseworkId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.onSuccess(SuccessCode._NO_CONTENT));
    }
}