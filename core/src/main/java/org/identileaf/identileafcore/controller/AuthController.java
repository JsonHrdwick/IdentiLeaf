package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.model.UserDTO;
import org.identileaf.identileafcore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
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
            model.addAttribute("error", "Password must be at least 8 characters long with at least 1 number and 1 special character");
            return null;
        } else if (authService.userExists(userDTO.getUsername())) {
            model.addAttribute("error", "User already exists");
            return null;
        } else {
            authService.registerNewUser(userDTO);
            model.addAttribute("error", "Successfully registered!");
            return "/index";
        }
    }
}

