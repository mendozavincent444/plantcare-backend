package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto getCurrentUserProfile() {

        User currentUser = this.getCurrentUser();

        return this.convertToDto(currentUser);
    }

    @Override
    public List<UserDto> getAllAdmins(int roleId) {
        List<User> admins = this.userRepository.findAllByRoleId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "role id", roleId));


        return admins
                .stream()
                .map(admin -> this.convertToDto(admin))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto banAdmin(UserDto admin, int adminId) {

        User user = this.userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", adminId));

        if (user.isAccountNonLocked()) {
            user.setAccountNonLocked(false);
        } else {
            //throw exception
        }

        User savedUser = this.userRepository.save(user);

        return this.convertToDto(savedUser);
    }

    @Override
    public UserDto reactivateAdmin(UserDto admin, int adminId) {

        User user = this.userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", adminId));

        if (user.isAccountNonLocked()) {
            // throw exception
        } else {
            user.setAccountNonLocked(true);
        }

        User savedUser = this.userRepository.save(user);

        return this.convertToDto(savedUser);
    }

    @Override
    public UserDto getAdminByUsername(String username) {

       // fix orElseThrow
        User admin = this.userRepository.findByUsername(username)
                .orElseThrow();

        return this.convertToDto(admin);
    }

    @Override
    public MessageResponseDto editAllowNotifications(ToggleAllowNotifications toggleAllowNotifications) {

        User currentUser = this.getCurrentUser();

        boolean isAllowNotifications = toggleAllowNotifications.isAllowNotifications();

        currentUser.setAllowNotifications(isAllowNotifications);

        this.userRepository.save(currentUser);

        return new MessageResponseDto("Notification settings changed.");
    }

    public User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }

    @Override
    public UserDto convertToDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .isAccountNonLocked(user.isAccountNonLocked())
                .isAllowNotifications(user.isAllowNotifications())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().getRoleName().name())
                .build();
    }
}
