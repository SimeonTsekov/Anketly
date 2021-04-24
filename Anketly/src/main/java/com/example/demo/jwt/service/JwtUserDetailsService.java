package com.example.demo.jwt.service;

import java.util.Optional;
import com.example.demo.entities.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userService.findByUsername(username);

        if(optionalUserEntity.isPresent()) {
            return optionalUserEntity.get().toUserDetails();
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!!!");
        }
    }
}
