package com.doittogether.platform.business.user;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.user.UserException;
import com.doittogether.platform.business.channel.ChannelService;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.entity.UserChannel;
import com.doittogether.platform.infrastructure.persistence.channel.UserChannelRepository;
import com.doittogether.platform.infrastructure.persistence.user.UserRepository;
import com.doittogether.platform.presentation.dto.user.request.UserUpdateRequest;
import com.doittogether.platform.presentation.dto.user.response.UserUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ChannelService channelService;

    private final UserRepository userRepository;
    private final UserChannelRepository userChannelRepository;

    @Override
    public User findByIdOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException(ExceptionCode.USER_NOT_FOUND));
    }

    @Override
    public boolean isSetup(User user) {
        return user.isSetup();
    }

    @Transactional
    @Override
    public UserUpdateResponse updateNickname(User user, UserUpdateRequest request) {
        user.updateNickName(request.nickName());
        user = userRepository.save(user);

        return UserUpdateResponse.from(user);
    }

    @Transactional
    @Override
    public void completeSetup(User user) {
        user.completeSetup();
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = findByIdOrThrow(userId);

        List<UserChannel> userChannels = userChannelRepository.findAllByUser(user);

        for (UserChannel userChannel : userChannels) {
            channelService.leaveChannel(user, userChannel.getChannel().getChannelId());
        }

        userRepository.delete(user);
    }
}
