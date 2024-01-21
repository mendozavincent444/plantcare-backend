package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.shared.UserDto;
import com.plantcare.serverapplication.usermanagement.role.Role;
import com.plantcare.serverapplication.usermanagement.role.RoleEnum;
import com.plantcare.serverapplication.usermanagement.role.RoleRepository;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @DisplayName("Unit test for get all admins")
    @Test
    public void givenRoleId_whenGetAllAdmins_thenReturnAdminList() {
        Role role = new Role(2, RoleEnum.ROLE_ADMIN);

        given(this.userRepository.findAllByRoleId(2)).willReturn(Optional.of(List.of(
                User.builder()
                        .email("benjamin@yahoo.com")
                        .isAccountNonLocked(true)
                        .username("benj123")
                        .firstName("Benjamin")
                        .lastName("Brown")
                        .password("sample")
                        .role(role)
                        .isAllowNotifications(true)
                        .build(),
                User.builder()
                        .email("jared@yahoo.com")
                        .isAccountNonLocked(true)
                        .username("jared123")
                        .firstName("Jared")
                        .lastName("John")
                        .password("sample")
                        .role(role)
                        .isAllowNotifications(true)
                        .build()
        )));

        List<UserDto> userDtos = this.userServiceImpl.getAllAdmins(role.getId());

        assertThat(userDtos).isNotNull();
        userDtos.forEach((userDto) -> assertThat(userDto.getRole()).isEqualTo("ROLE_ADMIN"));
    }
}
