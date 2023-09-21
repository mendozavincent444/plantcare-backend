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
public class AuthContoller {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final UserDetailsPasswordServiceImpl userDetailsPasswordService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthContoller(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            FarmRepository farmRepository,
            UserDetailsPasswordServiceImpl userDetailsPasswordService,
            UserDetailsServiceImpl userDetailsService,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.farmRepository = farmRepository;
        this.userDetailsPasswordService = userDetailsPasswordService;
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto LoginRequestDto) {

        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(LoginRequestDto.getUsername(), LoginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = this.jwtUtils.generateJwtCookie(userDetails);

        String role = userDetails.getAuthorities().stream().toList().get(0).getAuthority();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponseDto(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        role));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        if (this.userRepository.existsByUsername(registerRequestDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponseDto("Error: Username is already taken!"));
        }

        if (this.userRepository.existsByEmail(registerRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponseDto("Error: Email is already in use!"));
        }

        String role = registerRequestDto.getRole();

        Role userRole = null;

        if (role.equals("ROLE_FARMER")) {
            userRole = this.roleRepository.findByRoleName(RoleEnum.ROLE_FARMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        } else if (role.equals("ROLE_ADMIN")) {
            userRole = this.roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        }

        User user = User
                .builder()
                .email(registerRequestDto.getEmail())
                .status(true)
                .username(registerRequestDto.getUsername())
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .password(this.passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(userRole)
                .build();

        this.userRepository.save(user);

        return ResponseEntity.ok(new MessageResponseDto("User registered successfully!"));
    }


    @PostMapping("/farm/{farmId}/farmers/bulk-register")
    public ResponseEntity<?> registerFarmerBulk(@RequestParam("file") MultipartFile file, @PathVariable int farmId) throws IOException {

        if (!BulkRegisterCSVHelper.hasCSVFormat(file)) {
            return ResponseEntity.badRequest().body(new MessageResponseDto("Error: File format is incorrect!"));
        }

        List<BulkRegisterFarmerRequestDto> requestDtos = BulkRegisterCSVHelper.csvToBulkRegisterFarmerRequest(file.getInputStream());

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        List<Farm> farms = new ArrayList<>();
        farms.add(farm);

        Role role = this.roleRepository.findByRoleName(RoleEnum.ROLE_FARMER).orElseThrow();

        List<User> farmers = requestDtos.stream().map(request -> {
            return User
                    .builder()
                    .email(request.getEmail())
                    .status(true)
                    .username(request.getUsername())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .password(request.getPassword())
                    .role(role)
                    .build();
        }).collect(Collectors.toList());

        farmers.forEach((farmer) -> farm.getUsers().add(farmer));

        this.farmRepository.save(farm);

        return ResponseEntity.ok(new MessageResponseDto("Farmers registered successfully!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = this.jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponseDto("You've been signed out!"));
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
