package com.doittogether.platform.infrastructure.persistence;

import com.doittogether.platform.domain.entity.UserChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChannelRepository extends JpaRepository<Long, UserChannel> {
}
