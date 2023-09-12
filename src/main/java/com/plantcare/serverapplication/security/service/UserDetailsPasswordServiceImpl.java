package com.plantcare.serverapplication.security.service;

import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {

    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsService;

    public UserDetailsPasswordServiceImpl(
            UserRepository userRepository,
            UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        String username = user.getUsername();

        User userToUpdate = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username Not Found With Username: " + username));

        userToUpdate.setPassword(newPassword);

        return this.userDetailsService.loadUserByUsername(username);
    }
}
