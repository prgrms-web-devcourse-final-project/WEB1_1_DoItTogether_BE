package com.doittogether.platform.infrastructure.persistence;

import com.doittogether.platform.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);

    User findBySocialId(String socialId);
}
