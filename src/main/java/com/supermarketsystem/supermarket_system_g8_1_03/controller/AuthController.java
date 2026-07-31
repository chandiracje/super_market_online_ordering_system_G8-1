package com.supermarketsystem.supermarket_system_g8_1_03.controller;

import com.supermarketsystem.supermarket_system_g8_1_03.dto.UserRegistrationDto;
import com.supermarketsystem.supermarket_system_g8_1_03.models.Users;
import com.supermarketsystem.supermarket_system_g8_1_03.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // ----------------------------
    // LOGIN
    // ----------------------------
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Resolves to src/main/resources/templates/login.html
    }

    // ----------------------------
    // REGISTRATION
    // ----------------------------
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDto registrationDto, Model model) {

        // 1. Check if passwords match
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match. Please try again.");
            return "register";
        }

        // 2. Transfer DTO data to actual Entity
        Users newUser = new Users();
        newUser.setFullName(registrationDto.getFullName());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(registrationDto.getPassword());
        newUser.setDefaultShippingAddress(registrationDto.getDefaultShippingAddress());

        // 3. Save User
        try {
            userService.registerNewUser(newUser, "ROLE_USER");
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        return "redirect:/login?success";
    }

    // ----------------------------
    // EMAIL VERIFICATION
    // ----------------------------
    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("token") String token, Model model) {
        boolean isVerified = userService.verifyUserToken(token);
        if (isVerified) {
            // If verification is successful, send them to login with a success parameter
            return "redirect:/login?verified=true";
        } else {
            // If it fails (expired or invalid), send them back with an error
            model.addAttribute("error", "The verification link is invalid or has expired.");
            return "register";
        }
    }
}