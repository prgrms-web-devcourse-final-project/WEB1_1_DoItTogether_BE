package com.doittogether.platform.business.channel;

import com.doittogether.platform.business.invite.InviteLinkService;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.Role;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.entity.UserChannel;
import com.doittogether.platform.infrastructure.persistence.ChannelRepository;
import com.doittogether.platform.infrastructure.persistence.UserChannelRepository;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.doittogether.platform.presentation.dto.channel.request.ChannelRegisterRequest;
import com.doittogether.platform.presentation.dto.channel.response.ChannelListResponse;
import com.doittogether.platform.presentation.dto.channel.response.ChannelRegisterResponse;
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
