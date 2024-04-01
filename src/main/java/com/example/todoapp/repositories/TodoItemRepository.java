package com.example.todoapp.repositories;

import com.example.todoapp.models.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem,Integer> {
    List<TodoItem> findAllByOwner_Id(int userId);  
    List<TodoItem> findByOwner_IdAndDetailsContainingIgnoreCaseOrOwner_IdAndDescriptionContainingIgnoreCase(int userId1, String searchTerm1, int userId2, String searchTerm2);
}
