package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.UserDto;

import java.util.List;

public interface UserService {
    UserDto getCurrentUserProfile();
    List<UserDto> getAllAdmins(int roleId);

    UserDto banAdmin(UserDto admin, int adminId);

    UserDto reactivateAdmin(UserDto admin, int adminId);

    UserDto convertToDto(User user);
    UserDto getAdminByUsername(String username);
    MessageResponseDto editAllowNotifications(ToggleAllowNotifications toggleAllowNotifications);
    User getCurrentUser();
}
