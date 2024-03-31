package com.example.todoapp.controllers;

import com.example.todoapp.dto.Todo;
import com.example.todoapp.dto.TodoRequest;
import com.example.todoapp.services.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
@CrossOrigin("*")
public class TodoItemController {
    private final TodoItemService todoItemService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable int id) {
        todoItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<Todo> completeTodo(@PathVariable int id) {
        Todo completedTodo = todoItemService.complete(id);
        return ResponseEntity.ok(completedTodo);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<Todo>> getAllTodosForUser(@PathVariable int userId) {
        List<Todo> todos = todoItemService.getAll(userId);
        return ResponseEntity.ok(todos);
    }
    @PostMapping
    public ResponseEntity<Todo> saveTodo(@RequestBody TodoRequest request) {
        Todo savedTodo = todoItemService.save(request);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }
}
