package com.example.todoapp.repositories;

import com.example.todoapp.models.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem,Integer> {
    List<TodoItem> findAllByOwner_Id(int userId);
}
