package com.doittogether.platform.business.openai.util;

import com.doittogether.platform.application.global.exception.personality.PersonalityException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class TemplateUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String replaceSurveyResultWithJson(String template, List<String> surveyResultText) throws JsonProcessingException {
        try {
            String jsonArray = objectMapper.writeValueAsString(surveyResultText);

            return template.replace("${survey_result_text}", jsonArray);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public static List<String> mapJsonToKeywords(String jsonString) throws JsonProcessingException {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, List<String>>>() {})
                    .get("keywords");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
