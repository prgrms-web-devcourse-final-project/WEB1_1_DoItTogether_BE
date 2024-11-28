package com.doittogether.platform.business.channel;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.channel.ChannelException;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final UserRepository userRepository;
    private final UserChannelRepository userChannelRepository;
    private final ChannelRepository channelRepository;
    private final InviteLinkService inviteLinkService;

    @Override
    public ChannelListResponse getMyChannels(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));

        Pageable resolvedPageable = resolveSort(pageable);
        Page<UserChannel> userChannels = userChannelRepository.findByUser(user, resolvedPageable);

        Page<ChannelResponse> channelResponses = userChannels.map(ChannelResponse::from);

        return ChannelListResponse.of(user, channelResponses);
    }

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

        if (!userChannel.isRoleAdmin()) {
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

        Pageable resolvedPageable = resolveSort(pageable);
        Page<UserChannel> userChannels = userChannelRepository.findByChannel(channel, resolvedPageable);

        Page<UserChannelResponse> userChannelResponses = userChannels.map(UserChannelResponse::from);

        return ChannelUserListResponse.of(channel, userChannelResponses);
    }

    @Override
    public ChannelInviteLinkResponse generateInviteLink(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(ExceptionCode.CHANNEL_NOT_FOUND));

        String inviteLink = inviteLinkService.generateInviteLink(channelId);

        return ChannelInviteLinkResponse.of(channel, inviteLink);
    }

    @Override
    public ChannelJoinResponse joinChannelViaInviteLink(String email, String inviteLink) {
        Long channelId = inviteLinkService.validateInviteLink(inviteLink);

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(ExceptionCode.CHANNEL_NOT_FOUND));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));

        boolean isUserInChannel = userChannelRepository.existsByUserAndChannel(user, channel);
        if (isUserInChannel) {
            throw new ChannelException(ExceptionCode.USER_ALREADY_IN_CHANNEL);
        }

        UserChannel userChannel = UserChannel.of(user, channel, Role.PARTICIPANT);
        userChannelRepository.save(userChannel);

        return ChannelJoinResponse.of(channel);
    }

    @Override
    public ChannelKickUserResponse kickUserFromChannel(String email, Long channelId, ChannelKickUserRequest request) {
        User adminUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(ExceptionCode.CHANNEL_NOT_FOUND));

        UserChannel adminUserChannel = userChannelRepository.findByUserAndChannel(adminUser, channel)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_IN_CHANNEL));

        if (!adminUserChannel.isRoleAdmin()) {
            throw new ChannelException(ExceptionCode.CHANNEL_ACCESS_DENIED);
        }

        User targetUser = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_FOUND));

        UserChannel targetUserChannel = userChannelRepository.findByUserAndChannel(targetUser, channel)
                .orElseThrow(() -> new ChannelException(ExceptionCode.USER_NOT_IN_CHANNEL));

        userChannelRepository.delete(targetUserChannel);

        return ChannelKickUserResponse.from(targetUser);
    }

    private Pageable resolveSort(Pageable pageable) {
        Map<String, String> fieldMapping = Map.of(
                "userId", "user.userId",
                "nickName", "user.nickName",
                "email", "user.email",

                "channelId", "channel.channelId",
                "name", "channel.name"
        );

        List<Sort.Order> orders = pageable.getSort().stream()
                .map(order -> {
                    String mappedProperty = fieldMapping.getOrDefault(order.getProperty(), order.getProperty());
                    return new Sort.Order(order.getDirection(), mappedProperty);
                })
                .toList();

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}
