package com.doittogether.platform.business.channel;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.housework.HouseworkException;
import com.doittogether.platform.infrastructure.persistence.channel.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelValidator {
    private final ChannelRepository channelRepository;

    public void validateExistChannel(final Long channelId) {
        channelRepository.findById(channelId)
                .orElseThrow(() -> new HouseworkException(ExceptionCode.CHANNEL_NOT_FOUND));
    }
}
