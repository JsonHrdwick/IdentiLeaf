package org.identileaf.identileaf.service;

import org.identileaf.identileaf.model.User;
import org.identileaf.identileaf.model.UserDTO;
import org.identileaf.identileaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUser(UserDTO userDTO) {

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
    }
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean validateNewEmail(String email){
        String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        return email.matches(regex);
    }

    public boolean validateNewPassword(String password) {
        // Regex new password
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[a-zA-Z]).{8,}$";
        return password.matches(regex);
    }
}