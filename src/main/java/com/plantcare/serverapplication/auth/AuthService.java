package com.plantcare.serverapplication.auth;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.UserDto;
import com.plantcare.serverapplication.shared.UserInfoResponseDto;
import com.plantcare.serverapplication.usermanagement.user.UpdatePasswordDto;
import org.hibernate.sql.Update;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {

    AuthServiceLoginData authenticateUser(LoginRequestDto loginRequestDto);
    MessageResponseDto registerUser(RegisterRequestDto registerRequestDto);
    MessageResponseDto registerFarmerBulk(MultipartFile file, int farmId) throws IOException;
    AuthServiceLogoutData logoutUser();
    AuthServiceUpdatePasswordData changePassword(UpdatePasswordDto updatePasswordDto);
    UserInfoResponseDto updateUserInfo(UserDto userDto);
}
