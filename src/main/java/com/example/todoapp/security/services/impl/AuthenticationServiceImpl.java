package com.example.todoapp.security.services.impl;

import com.example.todoapp.dto.CreateUserRequest;
import com.example.todoapp.dto.LoginRequest;
import com.example.todoapp.exceptions.RecordNotFoundException;
import com.example.todoapp.models.TodoUser;
import com.example.todoapp.repositories.TodoUserRepository;
import com.example.todoapp.security.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final TodoUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Override
    public TodoUser signup(CreateUserRequest input) {
        TodoUser user = TodoUser.builder()
                .username(input.getUsername())
                .firstName(input.getFirstName())
                .lastLame(input.getLastName())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();
        return userRepository.save(user);
    }
    @Override
    public TodoUser authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(()-> new RecordNotFoundException("Invalid user name."));
    }
}
