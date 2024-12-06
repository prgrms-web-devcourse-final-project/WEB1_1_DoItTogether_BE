package com.doittogether.platform.business.personality;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.personality.PersonalityException;
import com.doittogether.platform.business.openai.ChatGPTService;
import com.doittogether.platform.business.openai.dto.ChatGPTResponse;
import com.doittogether.platform.business.openai.util.TemplateUtil;
import com.doittogether.platform.domain.entity.Personality;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.enumeration.PersonalityStatus;
import com.doittogether.platform.infrastructure.persistence.personality.PersonalityRepository;
import com.doittogether.platform.infrastructure.persistence.user.UserRepository;
import com.doittogether.platform.presentation.dto.personality.PersonalityRequestDto;
import com.doittogether.platform.presentation.dto.personality.PersonalityResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalityServiceImpl implements PersonalityService {

    private final ChatGPTService chatGPTService;

    private final PersonalityRepository personalityRepository;

    @Transactional
    @Override
    public PersonalityResponseDTO findKeywordsFromGPT(final User user, final PersonalityRequestDto request) {
        List<String> keywords = null;
        PersonalityStatus status = PersonalityStatus.VALID;

        try {
            ChatGPTResponse chatGPTResponse = chatGPTService.chat(request);
            String jsonResponse = chatGPTResponse.getChoices().get(0).getMessage().getContent();

            keywords = TemplateUtil.mapJsonToKeywords(jsonResponse); // gpt 가 추천해주는 키워드
        } catch (Exception e) {
            log.error("JSON 처리 중 예외 발생, 임시 데이터로 처리합니다. {}", e.getMessage());
            status = PersonalityStatus.INVALID;

            List<List<String>> keywordOptions = List.of( // 키워드 리스트 중 랜덤으로 선택
                    List.of("유연성마스터 🤸‍♂️", "활동가 🏃‍♀️", "조용한숲 🌳", "집안일멀미 😖"),
                    List.of("리더십왕 👑", "창의력 🌟", "열정불꽃🔥", "커뮤니케이션 달인 🗣️"),
                    List.of("분석가 🔍", "감성가득 💖", "지휘관 ⭐", "도전정신 🏔️"),
                    List.of("모험가 🚀", "평화주의자 🕊️", "호기심왕 👀", "아이디어 뱅크 💡"),
                    List.of("결단력 대장 🦸‍♂️", "사교가 💬", "차분함 😌", "다재다능 🎨")
            );

            Random random = new Random();
            keywords = keywordOptions.get(random.nextInt(keywordOptions.size()));
        }

        if (keywords == null || keywords.isEmpty()) {
            log.error("성향 데이터가 정상적으로 담기지 않았습니다.");
            throw new PersonalityException(ExceptionCode._INTERNAL_SERVER_ERROR);
        }

        savePersonalities(user, keywords, status);
        return PersonalityResponseDTO.from(keywords);
    }

    @Override
    public void savePersonalities(final User user, List<String> keywords, PersonalityStatus status) {
        personalityRepository.deleteByUser(user);

        keywords.forEach(keyword -> {
            Personality personality = Personality.of(keyword, user, status);
            personalityRepository.save(personality);
        });
    }

    @Override
    public PersonalityResponseDTO getUserPersonalities(User user) {
        List<String> keywords = personalityRepository.findByUser(user)
                .stream()
                .map(Personality::retrieveValue)
                .collect(Collectors.toList());

        return PersonalityResponseDTO.from(keywords);
    }
}
