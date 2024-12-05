package com.doittogether.platform.business.personality;

import com.doittogether.platform.business.openai.ChatGPTService;
import com.doittogether.platform.business.openai.dto.ChatGPTResponse;
import com.doittogether.platform.business.openai.util.TemplateUtil;
import com.doittogether.platform.domain.entity.Personality;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.personality.PersonalityRepository;
import com.doittogether.platform.infrastructure.persistence.user.UserRepository;
import com.doittogether.platform.presentation.dto.personality.PersonalityRequestDto;
import com.doittogether.platform.presentation.dto.personality.PersonalityResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalityServiceImpl implements PersonalityService {

    private final ChatGPTService chatGPTService;
    private final UserRepository userRepository;

    private final PersonalityRepository personalityRepository;

    @Transactional
    @Override
    public PersonalityResponseDTO findKeywordsFromGPT(final User user, final PersonalityRequestDto request) {
        ChatGPTResponse chatGPTResponse = chatGPTService.chat(request);
        String jsonResponse = chatGPTResponse.getChoices().get(0).getMessage().getContent();

        List<String> keywords = TemplateUtil.mapJsonToKeywords(jsonResponse);
        savePersonalities(user, keywords);
        return PersonalityResponseDTO.from(keywords);
    }

    @Override
    public void savePersonalities(final User user, List<String> keywords) {
        personalityRepository.deleteByUser(user);

        keywords.forEach(keyword -> {
            Personality personality = Personality.of(keyword, user);
            personalityRepository.save(personality);
        });
    }


}
