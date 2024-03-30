package com.example.todoapp.dto;

import lombok.Data;

@Data
public class TodoRequest {
    private int id;
    private String details;
    private String description;
}
