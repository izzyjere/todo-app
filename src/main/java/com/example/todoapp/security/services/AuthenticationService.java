package com.example.todoapp.security.services;

import com.example.todoapp.dto.CreateUserRequest;
import com.example.todoapp.dto.LoginRequest;
import com.example.todoapp.models.TodoUser;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    TodoUser signup(CreateUserRequest input);

    TodoUser authenticate(LoginRequest input);
}
