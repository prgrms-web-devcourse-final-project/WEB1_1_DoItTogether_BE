package com.doittogether.platform.business.invite;

import java.security.SecureRandom;

public class RandomAuthCode {
    // 인증 코드에 사용될 문자들
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    // 인증 코드의 길이 (6자리)
    private static final int CODE_LENGTH = 6;
    // 보안 강화를 위한 SecureRandom 사용
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 인증 코드를 생성하는 메소드
     *
     * @return 생성된 인증 코드 (Ex. wu2mf9)
     */
    public static String generate() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
