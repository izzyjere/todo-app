package com.example.todoapp.services;

import com.example.todoapp.models.TodoItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoItemService {
   TodoItem getById(int id);
   List<TodoItem> getAllByUserId(int userId);
   TodoItem create(String details,String description);
}
