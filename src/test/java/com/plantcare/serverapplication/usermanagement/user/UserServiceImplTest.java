package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.exception.UserAccountStatusException;
import com.plantcare.serverapplication.shared.UserDto;
import com.plantcare.serverapplication.usermanagement.role.Role;
import com.plantcare.serverapplication.usermanagement.role.RoleEnum;
import com.plantcare.serverapplication.usermanagement.role.RoleRepository;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
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

    @DisplayName("Unit test for deactivating admin")
    @Test
    public void givenAdminId_whenDeactivatingAdmin_thenDeactivateAdmin() {
        Role role = new Role(2, RoleEnum.ROLE_ADMIN);

        User user = User.builder()
                .id(1)
                .email("benjamin@yahoo.com")
                .isAccountNonLocked(true)
                .username("admin")
                .firstName("Benjamin")
                .lastName("Brown")
                .role(role)
                .password("sample")
                .isAllowNotifications(true)
                .build();

        given(this.userRepository.findById(1)).willReturn(Optional.of(user));
        given(this.userRepository.save(user)).willReturn(user);

        UserDto userDto = this.userServiceImpl.deactivateAdmin(1);

        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.isAccountNonLocked()).isEqualTo(false);
    }

    @DisplayName("Unit test for deactivating admin throws exception")
    @Test
    public void givenAdminId_whenDeactivatingAdmin_thenThrowsException() {
        Role role = new Role(2, RoleEnum.ROLE_ADMIN);

        User user = User.builder()
                .id(1)
                .email("benjamin@yahoo.com")
                .isAccountNonLocked(false)
                .username("admin")
                .firstName("Benjamin")
                .lastName("Brown")
                .role(role)
                .password("sample")
                .isAllowNotifications(true)
                .build();

        given(this.userRepository.findById(1)).willReturn(Optional.of(user));

        assertThrows(UserAccountStatusException.class, () -> this.userServiceImpl.deactivateAdmin(1));

        verify(this.userRepository, never()).save(any(User.class));
    }
}
