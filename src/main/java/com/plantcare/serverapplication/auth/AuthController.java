package com.plantcare.serverapplication.auth;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.security.jwt.JwtUtils;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.security.service.UserDetailsPasswordServiceImpl;
import com.plantcare.serverapplication.security.service.UserDetailsServiceImpl;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.UserInfoResponseDto;
import com.plantcare.serverapplication.usermanagement.role.Role;
import com.plantcare.serverapplication.usermanagement.role.RoleEnum;
import com.plantcare.serverapplication.usermanagement.role.RoleRepository;
import com.plantcare.serverapplication.usermanagement.user.UpdatePasswordDto;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import jakarta.mail.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequestDto) {

        AuthServiceLoginData authServiceLoginData = this.authService.authenticateUser(loginRequestDto);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, authServiceLoginData.getJwtCookie())
                .body(authServiceLoginData.getUserInfoResponseDto());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {

        MessageResponseDto messageResponseDto = this.authService.registerUser(registerRequestDto);

        return ResponseEntity
                .ok(messageResponseDto);
    }


    @PostMapping("/farm/{farmId}/farmers/bulk-register")
    public ResponseEntity<?> registerFarmerBulk(@RequestParam("file") MultipartFile file, @PathVariable int farmId) throws IOException {

        MessageResponseDto messageResponseDto = this.authService.registerFarmerBulk(file, farmId);

        return ResponseEntity
                .ok(messageResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {

        AuthServiceLogoutData authServiceLogoutData = this.authService.logoutUser();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authServiceLogoutData.getEmptyCookie())
                .body(authServiceLogoutData.getMessageResponseDto());
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {

        AuthServiceUpdatePasswordData authServiceUpdatePasswordData = this.authService.changePassword(updatePasswordDto);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, authServiceUpdatePasswordData.getUpdatedJwtCookie())
                .body(authServiceUpdatePasswordData.getMessageResponseDto());
    }

}
