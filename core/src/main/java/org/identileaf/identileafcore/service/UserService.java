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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Simplifies code so user calls inside method do not have to be wrapped in Optional casting or handle exceptions
    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Loads the requested user granted that the account is not locked out. Resets the status if the local time is greater
     * than the lockout time. Method is used by DaoAuthenticationProvider in SecurityConfig
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user.isAccountLocked()) {
            // Check if the lock period has passed
            if (user.getLockTime().plusMinutes(LOCK_DURATION_MINUTES).isBefore(LocalDateTime.now())) {
                user.setAccountLocked(false);
                user.setFailedLoginAttempts(0);
                userRepository.save(user);
            } else {
                throw new LockedException("Account locked");
            }
        } else { // User was logged in and is not locked out
            user.setAccountLocked(false);
            user.setFailedLoginAttempts(0);
            userRepository.save(user);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    /**
     * Handles the increment and recording of a failed login attempt. If max attempts are reached then method sets the
     * user account status to locked. Max attempts = 3, Lockout time = 5 min
     * @param username User's username
     */
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