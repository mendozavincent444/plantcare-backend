package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.shared.UserDto;
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
    public List<UserDto> getAllAdmins(int roleId) {
        List<User> admins = this.userRepository.findAllByRoleId(roleId).orElseThrow();


        return admins
                .stream()
                .map(admin -> this.convertToDto(admin))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto banAdmin(UserDto admin, int adminId) {

        User user = this.userRepository.findById(adminId).orElseThrow();

        if (user.isStatus()) {
            user.setStatus(false);
        } else {
            //throw exception
        }

        User savedUser = this.userRepository.save(user);

        return this.convertToDto(savedUser);
    }

    @Override
    public UserDto reactivateAdmin(UserDto admin, int adminId) {

        User user = this.userRepository.findById(adminId).orElseThrow();

        if (user.isStatus()) {
            // throw exception
        } else {
            user.setStatus(true);
        }

        User savedUser = this.userRepository.save(user);

        return this.convertToDto(savedUser);
    }

    private UserDto convertToDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .status(user.isStatus())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
