package com.example.todoapp;

import com.example.todoapp.exceptions.RecordNotFoundException;
import com.example.todoapp.dto.Todo;
import com.example.todoapp.dto.TodoRequest;
import com.example.todoapp.models.TodoItem;
import com.example.todoapp.models.TodoStatus;
import com.example.todoapp.models.TodoUser;
import com.example.todoapp.repositories.TodoItemRepository;
import com.example.todoapp.repositories.TodoUserRepository;
import com.example.todoapp.services.TodoItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TodoItemServiceTests {

    @Mock
    private TodoItemRepository todoItemRepository;

    @Mock
    private TodoUserRepository userRepository;

    @InjectMocks
    private TodoItemServiceImpl todoItemService;

    @Test
    public void testDelete() {
        int todoId = 1;
        when(todoItemRepository.findById(todoId)).thenReturn(Optional.of(new TodoItem()));

        assertDoesNotThrow(() -> todoItemService.delete(todoId));

        verify(todoItemRepository, times(1)).delete(any());
    }

    @Test
    public void testDelete_Throws_RecordNotFoundException() {
        int todoId = 1;
        when(todoItemRepository.findById(todoId)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> todoItemService.delete(todoId));
    }

    @Test
    public void testComplete() {
        int todoId = 1;
        var existingTodo = new TodoItem("Test todo","Test description",new TodoUser());
        existingTodo.setId(1);
        when(todoItemRepository.findById(todoId)).thenReturn(Optional.of(existingTodo));
        when(todoItemRepository.save(existingTodo)).thenReturn(existingTodo);
        Todo completedTodo = todoItemService.complete(todoId);
        assertNotNull(completedTodo);
        assertEquals(completedTodo.getStatus(), TodoStatus.COMPLETED);
    }

    @Test
    public void testComplete_Throws_RecordNotFoundException() {
        int todoId = 1;
        when(todoItemRepository.findById(todoId)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> todoItemService.complete(todoId));
    }

    @Test
    public void testSave_NewTodo() {
        TodoRequest request = new TodoRequest();
        request.setDescription("Test Description");
        request.setDetails("Test Details");

        UserDetails userDetails = User.withUsername("testUser").password("password").build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails,null));

        when(userRepository.findByUsername(userDetails.getUsername())).thenReturn(Optional.of(new TodoUser()));
        when(todoItemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Todo savedTodo = todoItemService.save(request);

        assertNotNull(savedTodo);
        assertEquals(request.getDescription(), savedTodo.getDescription());
        assertEquals(request.getDetails(), savedTodo.getDetails());
    }

    @Test
    public void testSave_UpdateTodo() {
        int todoId = 1;
        TodoRequest request = new TodoRequest();
        request.setId(todoId);
        request.setDescription("Updated Description");
        request.setDetails("Updated Details");
        var testUser = TodoUser.builder()
                               .id(1)
                               .username("wisdom")
                               .firstName("Wisdom")
                               .lastLame("Jere")
                               .build();
        var existingTodo = new TodoItem("Test todo","Test description",testUser);
        existingTodo.setId(1);
        when(todoItemRepository.findById(todoId)).thenReturn(Optional.of(existingTodo));
        when(todoItemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Todo updatedTodo = todoItemService.save(request);
        assertNotNull(updatedTodo);
        assertEquals(todoId, updatedTodo.getId());
        assertEquals(request.getDescription(), updatedTodo.getDescription());
        assertEquals(request.getDetails(), updatedTodo.getDetails());
    }
}
