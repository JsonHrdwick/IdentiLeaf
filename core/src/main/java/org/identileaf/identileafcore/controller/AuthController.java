package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.model.UserDTO;
import org.identileaf.identileafcore.repository.UserRepository;
import org.identileaf.identileafcore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;

@Controller
public class AuthController {

    private final AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(UserDTO userDTO, Model model){

        if (!authService.validateNewEmail(userDTO.getUsername())) {
            model.addAttribute("error", "Invalid email address");
            return null;
        } else if (!authService.validateNewPassword(userDTO.getPassword())) {
            model.addAttribute("error", "Invalid password");
            return null;
        } else {
            try {
                Authentication auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword(), new ArrayList<>())
                );

                if (auth.isAuthenticated()) {
                    return "/index";
                }
            } catch (AuthenticationException e) {
                model.addAttribute("error", "Invalid username or password");
            }

        }
        return null;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        if (!(userDTO.getPassword().equals(userDTO.getConfirmPassword()))) {
            model.addAttribute("error", "Passwords do not match");
            return null;
        } else if (!authService.validateNewEmail(userDTO.getUsername())) {
            model.addAttribute("error", "Invalid email address");
            return null;
        } else if (!authService.validateNewPassword(userDTO.getPassword())) {
            model.addAttribute("error", "Invalid password");
            return null;
        } else if (authService.userExists(userDTO.getUsername())) {
            model.addAttribute("error", "User already exists");
            return null;
        } else {
            System.out.println(userDTO.getPassword() + " " + passwordEncoder.encode(userDTO.getPassword()));

            authService.registerNewUser(userDTO);
            model.addAttribute("error", "Successfully registered!");
            return "/index";
        }
    }
}

