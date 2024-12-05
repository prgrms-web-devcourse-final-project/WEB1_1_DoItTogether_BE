package com.doittogether.platform.infrastructure.persistence.personality;

import com.doittogether.platform.domain.entity.Personality;
import com.doittogether.platform.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalityRepository extends JpaRepository<Personality, Long> {
    void deleteByUser(User user);
}