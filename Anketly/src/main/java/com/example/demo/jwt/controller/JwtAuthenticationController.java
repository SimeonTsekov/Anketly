package com.example.demo.jwt.controller;

import com.example.demo.entities.UserEntity;
import com.example.demo.jwt.config.JwtTokenUtil;
import com.example.demo.jwt.model.JwtRequest;
import com.example.demo.jwt.model.JwtResponse;
import com.example.demo.jwt.service.JwtUserDetailsService;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.reactor.DebugAgentEnvironmentPostProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Function;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtTokenUtil jwtTokenUtil,
                                       JwtUserDetailsService userDetailsService,
                                       UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel) throws Exception {

        System.out.println(userModel.getUsername() + " " + userModel.getPassword());
        UserEntity userEntity = userModel.toUserEntity();
        Optional<UserEntity> optionalUserEntity = userService.findByUsername(userEntity.getUsername());

        createUserIfNotExists(optionalUserEntity, userEntity);

        final String token = jwtTokenUtil.generateToken(userEntity.toUserDetails());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        authenticate(jwtRequest);

        System.out.println(jwtRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(jwtRequest.getUsername()));

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void createUserIfNotExists(Optional<UserEntity> optionalUserEntity, UserEntity userEntity) throws Exception {
        if(optionalUserEntity.isEmpty()) {
            userService.insertUser(userEntity);
        } else {
            throw new Exception("User with username " + userEntity.getUsername() + " already exists!!!");
        }
    }
}
