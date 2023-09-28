package com.plantcare.serverapplication.auth;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.security.jwt.JwtUtils;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.security.service.UserDetailsPasswordServiceImpl;
import com.plantcare.serverapplication.security.service.UserDetailsServiceImpl;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.UserDto;
import com.plantcare.serverapplication.shared.UserInfoResponseDto;
import com.plantcare.serverapplication.usermanagement.role.Role;
import com.plantcare.serverapplication.usermanagement.role.RoleEnum;
import com.plantcare.serverapplication.usermanagement.role.RoleRepository;
import com.plantcare.serverapplication.usermanagement.user.UpdatePasswordDto;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import jakarta.mail.Message;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final UserDetailsPasswordServiceImpl userDetailsPasswordService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(
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

    @Override
    public AuthServiceLoginData authenticateUser(LoginRequestDto loginRequestDto) {

        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = this.jwtUtils.generateJwtCookie(userDetails);

        User currentUser = this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        String role = userDetails.getAuthorities().stream().toList().get(0).getAuthority();

        UserInfoResponseDto userInfoResponseDto = UserInfoResponseDto.builder()
                .id(currentUser.getId())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .email(currentUser.getEmail())
                .username(currentUser.getUsername())
                .role(currentUser.getRole().getRoleName().name())
                .build();

        return new AuthServiceLoginData(jwtCookie.toString(), userInfoResponseDto);
    }

    @Override
    public MessageResponseDto registerUser(RegisterRequestDto registerRequestDto) {

        if (this.userRepository.existsByUsername(registerRequestDto.getUsername())) {
            throw new BadCredentialsException("Username is already taken!");
        }

        if (this.userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new BadCredentialsException("Email is already in use!");
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
                .isAccountNonLocked(true)
                .username(registerRequestDto.getUsername())
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .password(this.passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(userRole)
                .build();

        this.userRepository.save(user);

        return new MessageResponseDto("User registered successfully!");
    }

    @Override
    public MessageResponseDto registerFarmerBulk(MultipartFile file, int farmId) throws IOException {


        if (!BulkRegisterCSVHelper.hasCSVFormat(file)) {
            throw new IOException("File format is incorrect! Please try again.");
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
                    .isAccountNonLocked(true)
                    .username(request.getUsername())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .password(this.passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
        }).collect(Collectors.toList());

        farmers.forEach((farmer) -> farm.getUsers().add(farmer));

        this.farmRepository.save(farm);

        return new MessageResponseDto("Farmers registered successfully!");
    }

    @Override
    public AuthServiceLogoutData logoutUser() {
        ResponseCookie cookie = this.jwtUtils.getCleanJwtCookie();

        MessageResponseDto messageResponseDto = new MessageResponseDto("You've been signed out!");

        return new AuthServiceLogoutData(cookie.toString(), messageResponseDto);
    }

    @Override
    public AuthServiceUpdatePasswordData changePassword(UpdatePasswordDto updatePasswordDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetailsImpl user = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(userDetails.getUsername());

        if (!this.passwordEncoder.matches(updatePasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect. Please verify and try again.");
        }

        UserDetailsImpl updatedUserDetails = (UserDetailsImpl) this.userDetailsPasswordService
                .updatePassword(userDetails, this.passwordEncoder.encode(updatePasswordDto.getNewPassword()));

        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(updatedUserDetails.getUsername(), updatePasswordDto.getNewPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseCookie jwtCookie = this.jwtUtils.generateJwtCookie(updatedUserDetails);

        MessageResponseDto messageResponseDto = new MessageResponseDto("Password updated successfully!");

        return new AuthServiceUpdatePasswordData(jwtCookie.toString(), messageResponseDto);
    }

    @Override
    public UserInfoResponseDto updateUserInfo(UserDto userDto) {
        User currentUser = this.getCurrentUser();

        currentUser.setFirstName(userDto.getFirstName());
        currentUser.setLastName(userDto.getLastName());

        User savedUser = this.userRepository.save(currentUser);

        UserInfoResponseDto userInfoResponseDto = UserInfoResponseDto.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .role(savedUser.getRole().getRoleName().name())
                .build();

        return userInfoResponseDto;
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
