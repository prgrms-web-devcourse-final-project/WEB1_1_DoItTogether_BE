package com.doittogether.platform.presentation.dto.OAuth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserDTO {

    private Long kakaoId;
    private String nickname;
    private String email;
    private String role;
    private String accessToken;
    private String refreshToken;
}
