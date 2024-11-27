package com.doittogether.platform.business.channel;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.channel.ChannelException;
import com.doittogether.platform.domain.entity.*;
import com.doittogether.platform.infrastructure.persistence.ChannelRepository;
import com.doittogether.platform.infrastructure.persistence.UserChannelRepository;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.doittogether.platform.presentation.dto.channel.request.ChannelJoinRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelKickUserRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelRegisterRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelUpdateRequest;
import com.doittogether.platform.presentation.dto.channel.response.*;
import com.doittogether.platform.presentation.dto.housework.HouseworkResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final UserRepository userRepository;
    private final UserChannelRepository userChannelRepository;
    private final ChannelRepository channelRepository;
    // private final HouseowrkRepository houseowrkRepository;
    // private final InviteLinkService inviteLinkService;

    @Override
    @Transactional
    public ChannelRegisterResponse createChannel(String email, ChannelRegisterRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));

        Channel channel = ChannelRegisterRequest.toEntity(request);
        channel = channelRepository.save(channel);

        UserChannel userChannel = UserChannel.of(user, channel, Role.ADMIN);
        userChannelRepository.save(userChannel);

        return ChannelRegisterResponse.of(channel.getChannelId(), channel.getName());
    }

    @Override
    public ChannelUpdateResponse updateChannelName(String email, Long channelId, ChannelUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(ExceptionCode.CHANNEL_NOT_FOUND));

        UserChannel userChannel = userChannelRepository.findByUserAndChannel(user, channel)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_IN_CHANNEL));

        if (userChannel.getRole() != Role.ADMIN) {
            throw new ChannelException(ExceptionCode.CHANNEL_ACCESS_DENIED);
        }

        channel.updateName(request.name());
        Channel updatedChannel = channelRepository.save(channel);

        return new ChannelUpdateResponse(
                updatedChannel.getChannelId()
        );
    }

    @Override
    public ChannelUserListResponse getChannelUsers(String email, Long channelId, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(ExceptionCode.CHANNEL_NOT_FOUND));

        userChannelRepository.findByUserAndChannel(user, channel)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_IN_CHANNEL));

        Page<UserChannel> userChannels = userChannelRepository.findByChannel(channel, pageable);

        List<UserResponse> userResponses = userChannels.stream()
                .map(userChannel -> UserResponse.from(userChannel.getUser()))
                .toList();

        return ChannelUserListResponse.of(channel, userResponses);
    }

    @Override
    public ChannelInviteLinkResponse generateInviteLink(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(ExceptionCode.CHANNEL_NOT_FOUND));

//        String inviteLink = inviteLinkService.generateInviteLink(channelId);
//
//        return ChannelInviteLinkResponse.of(channel, inviteLink);

        return null;
    }

    @Override
    public ChannelJoinResponse joinChannelViaInviteLink(String email, ChannelJoinRequest request) {
//        Long channelId = inviteLinkService.validateInviteLink(request.inviteLink());
//
//        Channel channel = channelRepository.findById(channelId)
//                .orElseThrow(() -> new ChannelException(ExceptionCode.CHANNEL_NOT_FOUND));
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));
//
//        boolean isUserInChannel = userChannelRepository.existsByUserAndChannel(user, channel);
//        if (isUserInChannel) {
//            throw new ChannelException(ExceptionCode.USER_ALREADY_IN_CHANNEL);
//        }
//
//        UserChannel userChannel = UserChannel.of(user, channel, Role.PARTICIPANT);
//        userChannelRepository.save(userChannel);
//
//        return ChannelJoinResponse.of(channel, true);

        return null;
    }

    @Override
    public ChannelKickUserResponse kickUserFromChannel(String email, Long channelId, ChannelKickUserRequest request) {
        User adminUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(ExceptionCode.CHANNEL_NOT_FOUND));

        UserChannel adminUserChannel = userChannelRepository.findByUserAndChannel(adminUser, channel)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_IN_CHANNEL));

        if (adminUserChannel.getRole() != Role.ADMIN) {
            throw new ChannelException(ExceptionCode.CHANNEL_ACCESS_DENIED);
        }

        User targetUser = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));

        UserChannel targetUserChannel = userChannelRepository.findByUserAndChannel(targetUser, channel)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_IN_CHANNEL));

        userChannelRepository.delete(targetUserChannel);

        return ChannelKickUserResponse.from(targetUser);
    }

    @Override
    public ChannelHouseworkListResponse getHouseworkByDate(Long channelId, LocalDate targetDate, Pageable pageable) {
//        Channel channel = channelRepository.findById(channelId)
//                .orElseThrow(() -> new ChannelException(ExceptionCode.CHANNEL_NOT_FOUND));
//
//        Page<Housework> houseworks = houseworkRepository.findByChannelAndDate(channel, targetDate, pageable);
//
//        List<HouseworkResponse> houseworkResponses = new ArrayList<>();
//        if (!houseworks.isEmpty()) {
//            houseworkResponses = houseworks.stream()
//                    .map(housework -> HouseworkResponse.from(housework))
//                    .toList();
//        }
//
//        return ChannelHouseworkListResponse.of(channel, houseworkResponses);

        return null;
    }
}
