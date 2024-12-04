package com.doittogether.platform.business.user;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.user.request.UserUpdateRequest;
import com.doittogether.platform.presentation.dto.user.response.UserUpdateResponse;

public interface UserService {
    User findByIdOrThrow(Long id);

    boolean isSetup(User user);

    UserUpdateResponse updateNickname(User user, UserUpdateRequest request);

    void completeSetup(User user);
}
