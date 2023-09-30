package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.security.jwt.JwtUtils;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.security.service.UserDetailsPasswordServiceImpl;
import com.plantcare.serverapplication.security.service.UserDetailsServiceImpl;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.UserDto;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserDto> getCurrentUserProfile() {

        UserDto currentUser = this.userService.getCurrentUserProfile();

        return ResponseEntity.ok(currentUser);
    }


    @GetMapping("/roles/{roleId}")
    public ResponseEntity<List<UserDto>> getAllAdmins(@PathVariable int roleId) {

        List<UserDto> admins = this.userService.getAllAdmins(roleId);

        return ResponseEntity.ok(admins);
    }


    @PutMapping("/admins/{adminId}/ban")
    public ResponseEntity<UserDto> banAdmin(
            @RequestBody UserDto admin,
            @PathVariable int adminId
    ) {
        UserDto bannedAdmin = this.userService.banAdmin(admin, adminId);

        return ResponseEntity.ok(bannedAdmin);
    }

    @PutMapping("/admins/{adminId}/reactivate")
    public ResponseEntity<UserDto> reactivateAdmin(
            @RequestBody UserDto admin,
            @PathVariable int adminId
    ) {
        UserDto reactivatedAdmin = this.userService.reactivateAdmin(admin, adminId);

        return ResponseEntity.ok(reactivatedAdmin);
    }

    @GetMapping("/admins/{adminUsername}")
    public ResponseEntity<UserDto> getAdminByUsername(@PathVariable String adminUsername) {

        UserDto admin = this.userService.getAdminByUsername(adminUsername);

        return ResponseEntity.ok(admin);
    }






}
