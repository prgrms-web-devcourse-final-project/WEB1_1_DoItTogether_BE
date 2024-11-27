package com.doittogether.platform.infrastructure.persistence;

import com.doittogether.platform.domain.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
