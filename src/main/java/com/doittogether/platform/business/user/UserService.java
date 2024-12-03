package com.doittogether.platform.business.user;

import com.doittogether.platform.domain.entity.User;

public interface UserService {
    User findByIdOrThrow(Long id);
}
