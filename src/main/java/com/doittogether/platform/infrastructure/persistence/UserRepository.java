package com.doittogether.platform.infrastructure.persistence;

import com.doittogether.platform.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);

    User findBykakaoId(Long kakaoId);
}
