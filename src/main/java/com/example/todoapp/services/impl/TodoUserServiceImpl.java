package com.example.todoapp.services.impl;

import com.example.todoapp.exceptions.RecordNotFoundException;
import com.example.todoapp.models.TodoUser;
import com.example.todoapp.repositories.TodoUserRepository;
import com.example.todoapp.services.TodoUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoUserServiceImpl implements TodoUserService {
    private final TodoUserRepository repository;

    @Override
    public Optional<TodoUser> findByName(String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    public TodoUser save(TodoUser user) {
        return repository.save(user);
    }

    @Override
    public void delete(int id) {
       var user = repository.findById(id);
       if(user.isEmpty()){
           log.error("Record was not found.");
           throw new RecordNotFoundException("User record was not found.");
       }
       repository.delete(user.get());
    }
}
