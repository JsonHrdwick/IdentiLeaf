package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.model.User;
import org.identileaf.identileafcore.model.UserDTO;
import org.identileaf.identileafcore.repository.UserRepository;
import org.identileaf.identileafcore.service.AuthService;
import org.identileaf.identileafcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    private final AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 15; // in minutes
    @Autowired
    private UserService userService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(UserDTO userDTO, Model model){

        if (!authService.validateNewEmail(userDTO.getUsername())) {
            //model.addAttribute("error", "Invalid email address");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email address"));
        } else if (!authService.validateNewPassword(userDTO.getPassword())) {
            //model.addAttribute("error", "Invalid password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid password"));
        } else {
            try {
                Authentication auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword(), new ArrayList<>())
                );

                if (auth.isAuthenticated()) {
                    User user = (User) userService.loadUserByUsername(userDTO.getUsername());
                    user.setFailedLoginAttempts(0); // Reset on successful login
                    return ResponseEntity.ok(Map.of("redirectUrl","/login"));
                } else if (!auth.isAuthenticated()){
                    System.out.println("Attempt failed");
                    User user = (User) userService.loadUserByUsername(userDTO.getUsername());
                    System.out.println(user.getFailedLoginAttempts());

                }
            } catch (AuthenticationException e) {
                model.addAttribute("error", "Invalid username or password");
                User user = (User) userService.loadUserByUsername(userDTO.getUsername());
                //if (user.getFailedLoginAttempts() >= MAX_FAILED_ATTEMPTS &&
                    //user.getLastFailedLogin().plusMinutes(LOCK_TIME_DURATION).isAfter(LocalDateTime.now())){
                    //model.addAttribute("error", "Account Locked");
                    //return null;

                //} //else {
                    //user.setFailedLoginAttempts(user.getFailedLoginAttempts()+1);
                    //System.out.println("Attempt failed");
                    //System.out.println(user.getFailedLoginAttempts());
                    //user.setLastFailedLogin(LocalDateTime.now());
                    //if (user.getFailedLoginAttempts() >= MAX_FAILED_ATTEMPTS) {
                    //    model.addAttribute("error", "Maximum failed attempts reached");
                    //    return null;
                    //}
                //}
            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Some other error"));
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

