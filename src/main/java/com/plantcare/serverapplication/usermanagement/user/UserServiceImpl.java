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


        return admins.stream().map(admin -> {
            return UserDto
                    .builder()
                    .id(admin.getId())
                    .email(admin.getEmail())
                    .status(admin.isStatus())
                    .username(admin.getUsername())
                    .firstName(admin.getFirstName())
                    .lastName(admin.getLastName())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public UserDto banAdmin(UserDto admin, int adminId) {

        User user = this.userRepository.findById(adminId).orElseThrow();

        if (!user.isStatus()) {
            user.setStatus(true);
        } else {
            //throw exception
        }

        User savedUser = this.userRepository.save(user);

        return UserDto
                .builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .status(savedUser.isStatus())
                .username(savedUser.getUsername())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .build();
    }
}
