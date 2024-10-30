package org.identileaf.identileafcore.service;

import org.identileaf.identileafcore.repository.UserRepository;
import org.identileaf.identileafcore.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor injection for MyUserRepository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> myUserOptional = userRepository.findByUsername(username);
        if (myUserOptional.isPresent()) {
            User myUser = myUserOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(myUser.getUsername())
                    .password(myUser.getPassword())
                    .roles(myUser.getRole().split(","))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}