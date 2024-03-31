package com.example.todoapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {
    private String username;
    private String fullName;
    private int id;
}