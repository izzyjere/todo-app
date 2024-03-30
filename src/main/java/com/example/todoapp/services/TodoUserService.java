package com.example.todoapp.services;

import com.example.todoapp.models.TodoUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TodoUserService {
    Optional<TodoUser> findByName(String userName);
    TodoUser save(TodoUser user);
    void delete(int id);
}
