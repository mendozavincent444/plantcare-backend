package com.plantcare.serverapplication.auth;

import com.plantcare.serverapplication.shared.UserInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseCookie;
@Getter
@AllArgsConstructor
public class AuthServiceLoginData {
    private String jwtCookie;
    private UserInfoResponseDto userInfoResponseDto;
}
