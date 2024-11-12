package org.identileaf.identileafcore.service;

import org.identileaf.identileafcore.repository.UserRepository;
import org.identileaf.identileafcore.model.User;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static final long LOCK_DURATION_MINUTES = 5;
    private static final int MAX_ATTEMPTS = 3;
    private final UserRepository userRepository;

    // Constructor injection for MyUserRepository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user.isAccountLocked()) {
            // Check if the lock period has passed
            if (user.getLockTime().plusMinutes(LOCK_DURATION_MINUTES).isBefore(LocalDateTime.now())) {
                user.setAccountLocked(false); // Unlock the account
                user.setFailedLoginAttempts(0); // Reset attempts
                userRepository.save(user);
            } else {
                throw new LockedException("Account locked");
            }
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getAuthorities());
    }
    public void processFailedLoginAttempt(String username) {
        User user = findByUsername(username);
        if (user != null) {
            int newFailCount = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(newFailCount);
            System.out.println("New failed login attempt: " + newFailCount);

            if (newFailCount >= MAX_ATTEMPTS) {
                user.setAccountLocked(true);
                user.setLockTime(LocalDateTime.now());
            }
            userRepository.save(user);
        }
    }
}