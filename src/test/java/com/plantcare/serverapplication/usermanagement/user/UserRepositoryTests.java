package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.usermanagement.role.Role;
import com.plantcare.serverapplication.usermanagement.role.RoleEnum;
import com.plantcare.serverapplication.usermanagement.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
})
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private User user;
    @BeforeEach
    public void setup() {
        Role role = new Role(2, RoleEnum.ROLE_ADMIN);
        Role savedRole = this.roleRepository.save(role);

        this.user = User.builder()
                .email("benjamin@yahoo.com")
                .isAccountNonLocked(true)
                .username("benj123")
                .firstName("Benjamin")
                .lastName("Brown")
                .password("sample")
                .role(savedRole)
                .resetToken("ABCDEFGHIJKL")
                .isAllowNotifications(true)
                .build();
    }

    @DisplayName("JUnit test for finding user by username")
    @Test
    public void givenUsername_whenFindByUsername_thenUser() {

        User savedUser = this.userRepository.save(this.user);

        User searchedUser = this.userRepository.findByUsername(this.user.getUsername()).get();

        assertThat(searchedUser).isNotNull();
        assertThat(savedUser).isEqualTo(searchedUser);
    }
    @DisplayName("JUnit test for finding user by email")
    @Test
    public void givenEmail_whenFindByEmail_thenReturnUser() {
        User savedUser = this.userRepository.save(this.user);

        User searchedUser = this.userRepository.findByEmail(this.user.getEmail()).get();

        assertThat(searchedUser).isNotNull();
        assertThat(savedUser).isEqualTo(searchedUser);
    }

    @DisplayName("JUnit test for finding user by reset token")
    @Test
    public void givenResetToken_whenFindByResetToken_thenReturnUser() {
        User savedUser = this.userRepository.save(this.user);

        User searchedUser = this.userRepository.findByResetToken(this.user.getResetToken()).get();

        assertThat(searchedUser).isNotNull();
        assertThat(savedUser).isEqualTo(searchedUser);
    }
}

