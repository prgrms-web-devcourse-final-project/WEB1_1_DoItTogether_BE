package com.doittogether.platform.business.housework;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.housework.HouseworkValidationException;
import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.housework.HouseworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HouseworkValidator {
    private final HouseworkRepository houseworkRepository;

    public void validateExistHousework(final Long houseworkId) {
        houseworkRepository.findById(houseworkId)
                .orElseThrow(() -> new HouseworkValidationException(ExceptionCode.HOUSEWORK_NOT_FOUND));
    }

    public void validateEditableUser(final Housework housework, final User user) {
        if (housework.retrieveAssignee().retrieveUser().retrieveUserId() != user.retrieveUserId()) {
            throw new HouseworkValidationException(ExceptionCode.HOUSEWORK_NO_PERMISSION);
        }
    }
}
