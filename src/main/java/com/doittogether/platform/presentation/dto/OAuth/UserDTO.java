package com.doittogether.platform.presentation.dto.OAuth;

import com.doittogether.platform.domain.entity.ProfileImage;
import com.doittogether.platform.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class UserDTO {

    private Long kakaoId;
    private String nickName;
    private String email;
    private String role;
    private String accessToken;
    private String refreshToken;

    public static UserDTO of(Long kakaoId, String nickName, String email, String role, String accessToken, String refreshToken) {
        UserDTO userDTO = new UserDTO();
        userDTO.kakaoId = kakaoId;
        userDTO.nickName = nickName;
        userDTO.email = email;
        userDTO.role = role;
        userDTO.accessToken = accessToken;
        userDTO.refreshToken = refreshToken;
        return userDTO;
    }
}
