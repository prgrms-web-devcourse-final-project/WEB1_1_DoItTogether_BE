package com.doittogether.platform.infrastructure.persistence;

import com.doittogether.platform.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);

    @Query("SELECT u.profileImage.url FROM User u WHERE u.nickName = :nickName")
    Optional<String> findProfileImageUrlByNickName(@Param("nickName") String nickName);
}
