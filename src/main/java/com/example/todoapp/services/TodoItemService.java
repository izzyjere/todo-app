package com.example.todoapp.services;

import com.example.todoapp.dto.Todo;
import com.example.todoapp.dto.TodoRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoItemService {
   void delete(int id);
   Todo complete(int id);
   List<Todo> getAll(int userId);
   Todo save(TodoRequest request);
}
