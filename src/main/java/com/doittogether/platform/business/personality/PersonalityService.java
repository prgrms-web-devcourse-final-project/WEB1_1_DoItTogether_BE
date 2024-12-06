package com.doittogether.platform.business.personality;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.enumeration.PersonalityStatus;
import com.doittogether.platform.presentation.dto.personality.PersonalityRequestDto;
import com.doittogether.platform.presentation.dto.personality.PersonalityResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PersonalityService {
    PersonalityResponseDTO findKeywordsFromGPT(final User user, final PersonalityRequestDto request);
    void savePersonalities(final User user, final List<String> keywords, PersonalityStatus status);
    PersonalityResponseDTO getUserPersonalities(User user);
}
