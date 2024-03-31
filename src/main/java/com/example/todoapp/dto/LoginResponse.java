package com.example.todoapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private UserData userData;
    private TokenResponse tokenData;
}
