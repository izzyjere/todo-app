package com.example.todoapp.dto;

import java.util.Date;

import com.example.todoapp.models.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Todo {
    private int id;
    private String details;
    private String description;
    private TodoStatus status;
    private String owner;
    private String createdDate;
    private String completedDate;
    private boolean complete;
}
