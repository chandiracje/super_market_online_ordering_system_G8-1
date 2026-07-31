package com.supermarketsystem.supermarket_system_g8_1_03.service;

import com.supermarketsystem.supermarket_system_g8_1_03.models.Users;
import com.supermarketsystem.supermarket_system_g8_1_03.models.VerificationToken;
import com.supermarketsystem.supermarket_system_g8_1_03.repository.UserRepository;
import com.supermarketsystem.supermarket_system_g8_1_03.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Value("${app.url:https://localhost:8080}")
    private String appUrl;

    public Users registerNewUser(Users user, String role) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email address already registered.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        user.setEmailVerified(false); // Make sure it defaults to false

        Users savedUser = userRepository.save(user);

        // Generate Verification Token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, savedUser);
        tokenRepository.save(verificationToken);

        // Send Email
        emailService.sendVerificationEmail(savedUser.getEmail(), token, appUrl);

        return savedUser;
    }

    public boolean verifyUserToken(String token) {
        var tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return false;
        }

        VerificationToken verificationToken = tokenOpt.get();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(verificationToken); // Clean up expired token
            return false;
        }

        Users user = verificationToken.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);

        tokenRepository.delete(verificationToken); // Clean up used token
        return true;
    }
}