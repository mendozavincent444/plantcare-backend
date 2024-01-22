package com.plantcare.serverapplication.usermanagement.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantcare.serverapplication.security.TestSecurityConfig;
import com.plantcare.serverapplication.shared.UserDto;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenAdminUsername_whenGetAdminByUsername_thenReturnUserAdminDto() throws Exception {

        String adminUsername = "admin";

        UserDto user = UserDto.builder()
                .email("benjamin@yahoo.com")
                .isAccountNonLocked(true)
                .username("admin")
                .firstName("Benjamin")
                .lastName("Brown")
                .password("sample")
                .role("ROLE_ADMIN")
                .isAllowNotifications(true)
                .build();

        given(this.userService.getAdminByUsername(adminUsername)).willReturn(user);

        ResultActions response = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/admins/{adminUsername}", adminUsername));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.role", CoreMatchers.is("ROLE_ADMIN")));
    }

}
