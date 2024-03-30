package com.example.todoapp.controllers;

import com.example.todoapp.dto.CreateUserRequest;
import com.example.todoapp.dto.Result;
import com.example.todoapp.services.TodoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
  private TodoUserService userService;
  @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
  public Result<?> register(@RequestBody CreateUserRequest request){

    return Result.Fail("Failed to register user.");
  }
}
