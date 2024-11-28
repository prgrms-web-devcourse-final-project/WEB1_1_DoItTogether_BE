package com.doittogether.platform.business.channel;

import com.doittogether.platform.business.invite.InviteLinkService;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.Role;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.entity.UserChannel;
import com.doittogether.platform.infrastructure.persistence.ChannelRepository;
import com.doittogether.platform.infrastructure.persistence.UserChannelRepository;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.doittogether.platform.presentation.dto.channel.request.ChannelKickUserRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelRegisterRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelUpdateRequest;
import com.doittogether.platform.presentation.dto.channel.response.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ChannelServiceTest {
    @Mock private UserRepository userRepository;

    @Mock private UserChannelRepository userChannelRepository;

    @Mock private ChannelRepository channelRepository;

    @Mock private InviteLinkService inviteLinkService;

    @InjectMocks private ChannelServiceImpl channelService;

    @Test
    void 내가_속한_채널_리스트_조회() {
        String email = "doto@gmail.com";
        User mockUser = User.of("Test User", email, null);
        setField(mockUser, "userId", 1L);

        Channel mockChannel = Channel.
                builder().
                name("Test Channel").
                build();
        setField(mockChannel, "channelId", 1L);

        UserChannel mockUserChannel = UserChannel.of(mockUser, mockChannel, Role.ADMIN);
        setField(mockUserChannel, "userChannelId", 1L);

        Pageable pageable = PageRequest.of(0, 10);
        Page<UserChannel> userChannelsPage = new PageImpl<>(List.of(mockUserChannel), pageable, 1);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(userChannelRepository.findByUser(mockUser, pageable)).thenReturn(userChannelsPage);

        ChannelListResponse result = channelService.getMyChannels(email, pageable);

        assertNotNull(result);
        assertEquals(mockUser.getUserId(), result.userId());
        assertEquals(1, result.channelList().size());
        assertEquals("Test Channel", result.channelList().get(0).name());

        verify(userRepository, Mockito.times(1)).findByEmail(email);
        verify(userChannelRepository, Mockito.times(1)).findByUser(mockUser, pageable);
    }

    @Test
    void 채널_생성() {
        String email = "doto@gmail.com";
        String channelName = "Test Channel";

        User mockUser = User.of("Test User", email, null);
        setField(mockUser, "userId", 1L);

        ChannelRegisterRequest request = ChannelRegisterRequest.of(channelName);

        Channel mockChannel = Channel.builder()
                .name(channelName)
                .build();
        setField(mockChannel, "channelId", 1L);

        UserChannel mockUserChannel = UserChannel.of(mockUser, mockChannel, Role.ADMIN);
        setField(mockUserChannel, "userChannelId", 1L);

        lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        lenient().when(channelRepository.save(any(Channel.class))).thenReturn(mockChannel);
        lenient().when(userChannelRepository.save(any(UserChannel.class))).thenReturn(mockUserChannel);

        ChannelRegisterResponse response = channelService.createChannel(email, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockChannel.getChannelId(), response.channelId());
        Assertions.assertEquals(channelName, response.name());
    }

    @Test
    void 채널방_이름_수정() {
        String email = "doto@gmail.com";
        Long channelId = 1L;
        String newChannelName = "Updated Channel Name";

        User mockUser = User.of("Test User", email, null);
        setField(mockUser, "userId", 1L);

        Channel mockChannel = Channel.builder().name("Old Channel Name").build();
        setField(mockChannel, "channelId", channelId);

        UserChannel mockUserChannel = UserChannel.of(mockUser, mockChannel, Role.ADMIN);
        setField(mockUserChannel, "userChannelId", 1L);

        ChannelUpdateRequest request = new ChannelUpdateRequest(newChannelName);

        lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        lenient().when(channelRepository.findById(channelId)).thenReturn(Optional.of(mockChannel));
        lenient().when(userChannelRepository.findByUserAndChannel(mockUser, mockChannel)).thenReturn(Optional.of(mockUserChannel));
        lenient().when(channelRepository.save(any(Channel.class))).thenReturn(mockChannel);

        ChannelUpdateResponse response = channelService.updateChannelName(email, channelId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(channelId, response.channelId());

        verify(userRepository, times(1)).findByEmail(email);
        verify(channelRepository, times(1)).findById(channelId);
        verify(userChannelRepository, times(1)).findByUserAndChannel(mockUser, mockChannel);
        verify(channelRepository, times(1)).save(mockChannel);

        // Channel 이름 업데이트 확인
        Assertions.assertEquals(newChannelName, mockChannel.getName());
    }

    @Test
    void 채널에_포함된_유저_리스트_조회() {
        String email = "doto1@gmail.com";
        Long channelId = 1L;

        User mockUser = User.of("Test User", email, null);
        setField(mockUser, "userId", 1L);

        Channel mockChannel = Channel.builder().name("Test Channel").build();
        setField(mockChannel, "channelId", channelId);

        UserChannel mockUserChannel = UserChannel.of(mockUser, mockChannel, Role.PARTICIPANT);
        setField(mockUserChannel, "userChannelId", 1L);

        Pageable pageable = PageRequest.of(0, 10);
        UserChannel anotherUserChannel = UserChannel.of(
                User.of("Another User", "doto2@gmail.com", null),
                mockChannel,
                Role.PARTICIPANT
        );
        setField(anotherUserChannel, "userChannelId", 2L);

        Page<UserChannel> userChannelsPage = new PageImpl<>(List.of(mockUserChannel, anotherUserChannel), pageable, 2);

        lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        lenient().when(channelRepository.findById(channelId)).thenReturn(Optional.of(mockChannel));
        lenient().when(userChannelRepository.findByUserAndChannel(mockUser, mockChannel)).thenReturn(Optional.of(mockUserChannel));
        lenient().when(userChannelRepository.findByChannel(mockChannel, pageable)).thenReturn(userChannelsPage);

        ChannelUserListResponse response = channelService.getChannelUsers(email, channelId, pageable);

        assertNotNull(response);
        assertEquals(mockChannel.getChannelId(), response.channelId());
        assertEquals(2, response.userList().size());
        assertEquals("doto1@gmail.com", response.userList().get(0).email());
        assertEquals("doto2@gmail.com", response.userList().get(1).email());

        verify(userRepository, times(1)).findByEmail(email);
        verify(channelRepository, times(1)).findById(channelId);
        verify(userChannelRepository, times(1)).findByUserAndChannel(mockUser, mockChannel);
        verify(userChannelRepository, times(1)).findByChannel(mockChannel, pageable);
    }

    @Test
    void 채널_초대_링크_생성() {
        Long channelId = 1L;
        String mockInviteLink = "http://test.com/invite/abc123";

        Channel mockChannel = Channel.builder().name("Test Channel").build();
        setField(mockChannel, "channelId", channelId);

        when(channelRepository.findById(channelId)).thenReturn(Optional.of(mockChannel));
        when(inviteLinkService.generateInviteLink(channelId)).thenReturn(mockInviteLink);

        ChannelInviteLinkResponse response = channelService.generateInviteLink(channelId);

        assertNotNull(response);
        assertEquals(mockChannel.getChannelId(), response.channelId());
        assertEquals(mockInviteLink, response.inviteLink());

        verify(channelRepository, times(1)).findById(channelId);
        verify(inviteLinkService, times(1)).generateInviteLink(channelId);
    }

    @Test
    void 초대_링크를_통한_채널_입장() {
        String email = "doto@gmail.com";
        String inviteLink = "http://test.com/invite/abc123";
        Long channelId = 1L;

        User mockUser = User.of("Test User", email, null);
        setField(mockUser, "userId", 1L);

        Channel mockChannel = Channel.builder().name("Test Channel").build();
        setField(mockChannel, "channelId", channelId);

        lenient().when(inviteLinkService.validateInviteLink(inviteLink)).thenReturn(channelId);
        lenient().when(channelRepository.findById(channelId)).thenReturn(Optional.of(mockChannel));
        lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        lenient().when(userChannelRepository.existsByUserAndChannel(mockUser, mockChannel)).thenReturn(false);

        ChannelJoinResponse response = channelService.joinChannelViaInviteLink(email, inviteLink);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockChannel.getChannelId(), response.channelId());

        verify(inviteLinkService, times(1)).validateInviteLink(inviteLink);
        verify(channelRepository, times(1)).findById(channelId);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userChannelRepository, times(1)).existsByUserAndChannel(mockUser, mockChannel);
        verify(userChannelRepository, times(1)).save(any(UserChannel.class));
    }

    @Test
    void 특정_유저_추방() {
        String adminEmail = "admin@example.com";
        String targetEmail = "doto@example.com";
        Long channelId = 1L;

        User adminUser = User.of("Admin User", adminEmail, null);
        setField(adminUser, "userId", 1L);

        User targetUser = User.of("Target User", targetEmail, null);
        setField(targetUser, "userId", 2L);

        Channel mockChannel = Channel.builder().name("Test Channel").build();
        setField(mockChannel, "channelId", channelId);

        UserChannel adminUserChannel = UserChannel.of(adminUser, mockChannel, Role.ADMIN);
        setField(adminUserChannel, "userChannelId", 1L);

        UserChannel targetUserChannel = UserChannel.of(targetUser, mockChannel, Role.PARTICIPANT);
        setField(targetUserChannel, "userChannelId", 2L);

        ChannelKickUserRequest request = new ChannelKickUserRequest(targetEmail);

        lenient().when(userRepository.findByEmail(adminEmail)).thenReturn(Optional.of(adminUser));
        lenient().when(channelRepository.findById(channelId)).thenReturn(Optional.of(mockChannel));
        lenient().when(userChannelRepository.findByUserAndChannel(adminUser, mockChannel)).thenReturn(Optional.of(adminUserChannel));
        lenient().when(userRepository.findByEmail(targetEmail)).thenReturn(Optional.of(targetUser));
        lenient().when(userChannelRepository.findByUserAndChannel(targetUser, mockChannel)).thenReturn(Optional.of(targetUserChannel));

        ChannelKickUserResponse response = channelService.kickUserFromChannel(adminEmail, channelId, request);

        assertNotNull(response);
        assertEquals(targetUser.getEmail(), response.email());
        assertEquals(targetUser.getNickName(), response.nickName());

        verify(userRepository, times(1)).findByEmail(adminEmail);
        verify(channelRepository, times(1)).findById(channelId);
        verify(userChannelRepository, times(1)).findByUserAndChannel(adminUser, mockChannel);
        verify(userRepository, times(1)).findByEmail(targetEmail);
        verify(userChannelRepository, times(1)).findByUserAndChannel(targetUser, mockChannel);
        verify(userChannelRepository, times(1)).delete(targetUserChannel);
    }

    // id 값 설정할 수 있도록 임시
    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
