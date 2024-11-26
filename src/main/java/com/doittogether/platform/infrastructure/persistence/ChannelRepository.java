package com.doittogether.platform.infrastructure.persistence;

import com.doittogether.platform.domain.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Long, Channel> {
}
