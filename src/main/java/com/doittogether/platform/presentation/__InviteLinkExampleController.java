package com.doittogether.platform.presentation;

import com.doittogether.platform.business.invite.InviteLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invite")
@RequiredArgsConstructor
public class __InviteLinkExampleController {

    private final InviteLinkService inviteLinkService;

    /**
     * 채널 ID로 초대 링크를 생성합니다.
     *
     * @param channelId 채널 ID
     * @return 생성된 초대 링크
     */
    @PostMapping("/{channelId}")
    public ResponseEntity<String> createInviteLink(@PathVariable Long channelId) {
        String inviteLink = inviteLinkService.generateInviteLink(channelId);
        return ResponseEntity.ok(inviteLink);
    }

    /**
     * 초대 코드를 검증하고 해당 채널 ID를 반환합니다.
     *
     * @param inviteCode 초대 코드
     * @return 유효한 초대 코드의 채널 ID
     */
    @GetMapping("/{inviteCode}")
    public ResponseEntity<Long> validateInviteCode(@PathVariable String inviteCode) {
        Long channelId = inviteLinkService.validateInviteLink(inviteCode);
        return ResponseEntity.ok(channelId);
    }
}
