package com.doittogether.platform.business.channel;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.channel.ChannelValidationException;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.channel.ChannelRepository;
import com.doittogether.platform.infrastructure.persistence.channel.UserChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelValidator {
    private final ChannelRepository channelRepository;
    private final UserChannelRepository userChannelRepository;

    public void validateExistChannel(final Long channelId) {
        channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelValidationException(ExceptionCode.CHANNEL_NOT_FOUND));
    }

    public Channel validateAndGetChannel(final Long channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelValidationException(ExceptionCode.CHANNEL_NOT_FOUND));
    }

    public void checkChannelParticipation(final User loginUser, final Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelValidationException(ExceptionCode.CHANNEL_NOT_FOUND));

        userChannelRepository.findByUserAndChannel(loginUser, channel)
                .orElseThrow(() -> new ChannelValidationException(ExceptionCode.USER_NOT_IN_CHANNEL));
    }

}
