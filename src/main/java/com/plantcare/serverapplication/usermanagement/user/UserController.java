package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.security.jwt.JwtUtils;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.security.service.UserDetailsPasswordServiceImpl;
import com.plantcare.serverapplication.security.service.UserDetailsServiceImpl;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsPasswordServiceImpl userDetailsPasswordService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    public UserController(
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserDetailsPasswordServiceImpl userDetailsPasswordService,
            UserDetailsServiceImpl userDetailsService,
            JwtUtils jwtUtils
    ) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsPasswordService = userDetailsPasswordService;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetailsImpl user = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(userDetails.getUsername());

        if (!this.passwordEncoder.matches(updatePasswordDto.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponseDto("Current password is incorrect. Please verify and try again."));
        } else {
            UserDetailsImpl updatedUserDetails = (UserDetailsImpl) this.userDetailsPasswordService
                    .updatePassword(userDetails, this.passwordEncoder.encode(updatePasswordDto.getNewPassword()));

            Authentication authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(updatedUserDetails.getUsername(), updatePasswordDto.getNewPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            ResponseCookie jwtCookie = this.jwtUtils.generateJwtCookie(updatedUserDetails);

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new MessageResponseDto("Password updated successfully!"));
        }
    }
}
