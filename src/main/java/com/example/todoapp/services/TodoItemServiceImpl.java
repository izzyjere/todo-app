package com.example.todoapp.services;

import com.example.todoapp.dto.Todo;
import com.example.todoapp.dto.TodoRequest;
import com.example.todoapp.exceptions.RecordNotFoundException;
import com.example.todoapp.models.TodoItem;
import com.example.todoapp.repositories.TodoItemRepository;
import com.example.todoapp.repositories.TodoUserRepository;
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
    private final TodoItemRepository todoItemRepository;
    private final TodoUserRepository userRepository;
    private Todo map(TodoItem todoItem) {
        return Todo.builder()
                .status(todoItem.getStatus())
                .description(todoItem.getDescription())
                .details(todoItem.getDetails())
                .id(todoItem.getId())
                .owner(todoItem.getOwner().toString())
                .build();
    }

    @Override
    public void delete(int id) {
        var todoItem = todoItemRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Todo item with id {" + id + "} was not found."));
        todoItemRepository.delete(todoItem);
    }

    @Override
    public Todo complete(int id) {
        var todoItem = todoItemRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Todo item with id {" + id + "} was not found."));
        todoItem.markComplete();
        return map(todoItemRepository.save(todoItem));
    }

    @Override
    public List<Todo> getAll(int userId) {
        return todoItemRepository.findAllByOwner_Id(userId).stream().map(this::map).toList();
    }
    @Override
    public Todo save(TodoRequest request) {
        TodoItem todoItem;
        if(request.getId() == 0){
            var currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            var owner = userRepository.findByUsername(currentUser.getUsername());
            if (owner.isEmpty()) {
                log.error("Invalid authentication state. Todo can't be created.");
                return null;
            }

            todoItem = new TodoItem(request.getDetails(), request.getDescription(), owner.get());
        }else {
            //Editing
            todoItem = todoItemRepository.findById(request.getId()).orElseThrow(() -> new RecordNotFoundException("Todo item with id {" + request.getId() + "} was not found."));
            todoItem.setDescription(request.getDescription());
            todoItem.setDetails(request.getDetails());
        }

        return map(todoItemRepository.save(todoItem));
    }
}
