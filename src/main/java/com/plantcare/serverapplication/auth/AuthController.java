package com.plantcare.serverapplication.auth;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.UserDto;
import com.plantcare.serverapplication.shared.UserInfoResponseDto;
import com.plantcare.serverapplication.usermanagement.user.UpdatePasswordDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {

        AuthServiceLoginData authServiceLoginData = this.authService.authenticateUser(loginRequestDto);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, authServiceLoginData.getJwtCookie())
                .body(authServiceLoginData.getUserInfoResponseDto());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {

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
    public ResponseEntity<?> changePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {

        AuthServiceUpdatePasswordData authServiceUpdatePasswordData = this.authService.changePassword(updatePasswordDto);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, authServiceUpdatePasswordData.getUpdatedJwtCookie())
                .body(authServiceUpdatePasswordData.getMessageResponseDto());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponseDto> forgotPasswordRequest(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) {

        MessageResponseDto messageResponseDto = this.authService.forgotPasswordRequest(forgotPasswordRequestDto);

        return ResponseEntity.ok(messageResponseDto);
    }

    @PostMapping("/confirm-request")
    public ResponseEntity<MessageResponseDto> confirmPasswordRequest(@RequestParam("token") String token, @RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) {

        MessageResponseDto messageResponseDto = this.authService.forgotPasswordReset(token, forgotPasswordRequestDto.getNewPassword());

        return ResponseEntity.ok(messageResponseDto);
    }


    @PutMapping("/update-profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserDto userDto) {

        UserInfoResponseDto userInfoResponseDto = this.authService.updateUserInfo(userDto);

        return ResponseEntity.ok(userInfoResponseDto);
    }

}
