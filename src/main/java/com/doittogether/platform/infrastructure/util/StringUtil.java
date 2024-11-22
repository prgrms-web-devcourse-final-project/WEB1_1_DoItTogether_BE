package com.doittogether.platform.infrastructure.util;

public class StringUtil {

    /**
     * 문자열들을 점(.)으로 연결합니다.
     *
     * @param 문자열들 점으로 연결할 문자열들 (가변 인자)
     * @return 점으로 연결된 단일 문자열, 또는 입력이 없으면 빈 문자열 반환
     */
    public static String joinWithDot(String... parts) {
        if (parts == null || parts.length == 0) {
            return "";
        }
        return String.join(".", parts);
    }
}
