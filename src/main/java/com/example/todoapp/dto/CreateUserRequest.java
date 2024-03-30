package com.example.todoapp.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
