package com.doittogether.platform.business.openai.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class TemplateUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String replaceSurveyResultWithJson(String template, List<String> surveyResultText) {
        try {
            String jsonArray = objectMapper.writeValueAsString(surveyResultText);

            return template.replace("${survey_result_text}", jsonArray);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert survey results to JSON", e);
        }
    }

    public static List<String> mapJsonToKeywords(String jsonString) {
        try {
            // JSON 문자열을 파싱하여 "keywords" 배열 추출
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, List<String>>>() {})
                    .get("keywords");
        } catch (Exception e) {
            throw new RuntimeException("Failed to map JSON to keywords list", e);
        }
    }
}
