package com.doittogether.platform.business.user;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.user.UserException;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.doittogether.platform.presentation.dto.user.request.UserUpdateRequest;
import com.doittogether.platform.presentation.dto.user.response.UserResponse;
import com.doittogether.platform.presentation.dto.user.response.UserUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByIdOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException(ExceptionCode.USER_NOT_FOUND));
    }

    @Override
    public boolean isProfileComplete(User user) {
        return user.isProfileComplete();
    }

    @Transactional
    @Override
    public UserUpdateResponse updateNickname(User user, UserUpdateRequest request) {
        user.updateNickName(request.nickName());
        user = userRepository.save(user);

        return UserUpdateResponse.from(user);
    }

    @Transactional
    @Override
    public void completeProfile(User user) {
        user.completeProfileSetup();
        userRepository.save(user);
    }
}
