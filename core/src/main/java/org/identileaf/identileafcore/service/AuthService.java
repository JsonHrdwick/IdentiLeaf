package org.identileaf.identileafcore.service;

import jakarta.servlet.http.HttpServletRequest;
import org.identileaf.identileafcore.model.User;
import org.identileaf.identileafcore.model.UserDTO;
import org.identileaf.identileafcore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, HttpServletRequest httpServletRequest) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.httpServletRequest = httpServletRequest;
    }

    public void registerNewUser(UserDTO userDTO) {

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("USER");
        user.setAccountLocked(false);

        userRepository.save(user);
        autoLoginUser(user);
    }

    private void autoLoginUser(User user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        httpServletRequest.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
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