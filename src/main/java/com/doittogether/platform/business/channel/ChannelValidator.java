package com.doittogether.platform.business.channel;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.channel.ChannelValidationException;
import com.doittogether.platform.application.global.exception.housework.HouseworkException;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.infrastructure.persistence.channel.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelValidator {
    private final ChannelRepository channelRepository;

    public void validateExistChannel(final Long channelId) {
        channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelValidationException(ExceptionCode.CHANNEL_NOT_FOUND));
    }

    public Channel validateAndGetChannel(final Long channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelValidationException(ExceptionCode.CHANNEL_NOT_FOUND));
    }
}
