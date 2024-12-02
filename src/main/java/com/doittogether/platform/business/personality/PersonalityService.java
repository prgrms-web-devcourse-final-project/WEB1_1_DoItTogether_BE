package com.doittogether.platform.business.personality;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.personality.PersonalityRequestDto;
import com.doittogether.platform.presentation.dto.personality.PersonalityResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class PersonalityService {
    public PersonalityResponseDTO findKeywordsFromGPT(final User user, final PersonalityRequestDto request) {
        return null;
    }
}
