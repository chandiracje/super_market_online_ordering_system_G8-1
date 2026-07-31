package com.supermarketsystem.supermarket_system_g8_1_03.service;

import com.supermarketsystem.supermarket_system_g8_1_03.models.Users;
import com.supermarketsystem.supermarket_system_g8_1_03.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Find the user by their email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password."));

        // 2. Enforce the Email Verification Rule
        if (!user.isEmailVerified()) {
            throw new DisabledException("Your email address is not verified yet. Please check your inbox.");
        }

        // 3. Hand the user over to Spring Security for password validation
        return new User(
                user.getEmail(),
                user.getPassword(), // The BCrypt hashed password
                true, // isEnabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}