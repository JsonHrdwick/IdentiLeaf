package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.model.UserDTO;
import org.identileaf.identileafcore.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Passwords do not match"));
        } else if (!registerService.validateNewEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid email address"));
        } else if (!registerService.validateNewPassword(userDTO.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password must be at least 8 characters long with at least 1 number and 1 special character"));
        } else if (registerService.userExists(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "User already exists"));
        } else {
            registerService.registerNewUser(userDTO);
            return ResponseEntity.ok(Map.of("message", "Successfully registered!"));
        }
    }
}

