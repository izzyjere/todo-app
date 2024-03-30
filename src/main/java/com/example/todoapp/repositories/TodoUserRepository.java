package com.example.todoapp.repositories;

import com.example.todoapp.models.TodoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoUserRepository extends JpaRepository<TodoUser,Integer> {
    Optional<TodoUser> findByUsername(String username);
}