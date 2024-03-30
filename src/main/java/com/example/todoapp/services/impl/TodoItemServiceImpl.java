package com.example.todoapp.services.impl;

import com.example.todoapp.exceptions.RecordNotFoundException;
import com.example.todoapp.models.TodoItem;
import com.example.todoapp.repositories.TodoItemRepository;
import com.example.todoapp.repositories.TodoUserRepository;
import com.example.todoapp.services.TodoItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class TodoItemServiceImpl implements TodoItemService {
    private  final TodoItemRepository todoItemRepository;
    private final TodoUserRepository userRepository;
    @Override
    public TodoItem getById(int id) {
        var todoItem = todoItemRepository.findById(id);
        return todoItem.orElseThrow(()-> new RecordNotFoundException("Todo item with id {"+ id +"} was not found."));
    }

    @Override
    public List<TodoItem> getAllByUserId(int userId) {
        return todoItemRepository.findAllByOwner_Id(userId);
    }

    @Override
    public TodoItem create(String details, String description) {
        var currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var owner = userRepository.findByUsername(currentUser.getUsername());
        if(owner.isEmpty()){
            log.error("Invalid authentication state. Todo can't be created.");
           return null;
        }

        var newTodo = new TodoItem(details,description,owner.get());
        return todoItemRepository.save(newTodo);
    }
}
