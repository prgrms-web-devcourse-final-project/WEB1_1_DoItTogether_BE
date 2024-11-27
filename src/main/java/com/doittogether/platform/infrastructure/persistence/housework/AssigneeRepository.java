package com.doittogether.platform.infrastructure.persistence.housework;

import com.doittogether.platform.domain.entity.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssigneeRepository extends JpaRepository<Assignee, Long> {
    Assignee findByUserUserId(Long userId);
}
