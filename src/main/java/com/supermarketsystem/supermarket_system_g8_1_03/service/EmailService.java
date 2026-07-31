package com.supermarketsystem.supermarket_system_g8_1_03.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String toEmail, String token, String appUrl) {
        String verificationUrl = appUrl + "/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Verify Your Supermarket Account");
        message.setText("Thank you for registering! Please click the link below to verify your email address:\n\n"
                + verificationUrl + "\n\nThis link will expire in 24 hours.");

        mailSender.send(message);
    }
}