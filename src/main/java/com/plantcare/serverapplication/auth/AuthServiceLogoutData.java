package com.plantcare.serverapplication.auth;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthServiceLogoutData {
    private String emptyCookie;
    private MessageResponseDto messageResponseDto;
}
