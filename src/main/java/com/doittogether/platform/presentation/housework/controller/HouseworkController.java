package com.doittogether.platform.presentation.housework.controller;

import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.housework.dto.HouseworkRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface HouseworkController {
    public ResponseEntity<SuccessResponse<Void>> addHousework(@AuthenticationPrincipal User user,
                                                              @PathVariable("groupId") Long groupId,
                                                              @RequestBody HouseworkRequest request);

    public ResponseEntity<SuccessResponse<Void>> updateHousework(
            @AuthenticationPrincipal User user,
            @PathVariable("groupId") Long groupId,
            @PathVariable(name = "houseworkId") Long id,
            @RequestBody @Valid HouseworkRequest request);

    public ResponseEntity<SuccessResponse<Void>> deleteHousework(@AuthenticationPrincipal User user,
                                                                 @PathVariable("groupId") Long groupId,
                                                                 @PathVariable(name = "houseworkId") Long id);
}
