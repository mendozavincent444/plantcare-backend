package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.usermanagement.role.Role;
import com.plantcare.serverapplication.usermanagement.role.RoleEnum;
import com.plantcare.serverapplication.usermanagement.role.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.flyway.enabled=false"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @DisplayName("JUnit test for finding user by username")
    @Test
    public void givenUsername_whenFindByUsername_thenUser() {

        Role role = new Role(1, RoleEnum.ROLE_ADMIN);

        User user = User.builder()
                .email("benjamin@yahoo.com")
                .isAccountNonLocked(true)
                .username("benj123")
                .firstName("Benjamin")
                .lastName("Brown")
                .password("sample")
                .role(role)
                .isAllowNotifications(true)
                .build();
        User savedUser = this.userRepository.save(user);

        User searchedUser = this.userRepository.findByUsername(user.getUsername()).get();

        assertThat(searchedUser).isNotNull();
        assertThat(savedUser).isEqualTo(searchedUser);
    }
}
