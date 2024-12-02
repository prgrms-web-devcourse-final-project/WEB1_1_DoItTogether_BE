package com.doittogether.platform.infrastructure.persistence.housework;

import com.doittogether.platform.domain.entity.Assignee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssigneeRepository extends JpaRepository<Assignee, Long> {
    Optional<Assignee> findByUserUserId(Long userId);
}
