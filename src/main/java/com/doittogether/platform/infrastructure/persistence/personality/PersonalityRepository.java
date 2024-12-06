package com.doittogether.platform.infrastructure.persistence.personality;

import com.doittogether.platform.domain.entity.Personality;
import com.doittogether.platform.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalityRepository extends JpaRepository<Personality, Long> {
    List<Personality> findByUser(User user);
    void deleteByUser(User user);
}