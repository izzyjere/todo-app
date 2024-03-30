package com.example.todoapp.controllers;

import com.example.todoapp.dto.CreateUserRequest;
import com.example.todoapp.dto.LoginRequest;
import com.example.todoapp.dto.Result;
import com.example.todoapp.dto.TokenResponse;
import com.example.todoapp.security.services.AuthenticationService;
import com.example.todoapp.security.services.TokenService;
import com.example.todoapp.services.TodoUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/authentication")
@Log4j2
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @PostMapping("/signup")
    @ResponseBody
    public Result<?> register(@RequestBody CreateUserRequest request) {
        var registeredUser = authenticationService.signup(request);
        if (registeredUser == null) {
            return Result.Fail("Failed to register user.");
        }
        return Result.Success(registeredUser.toString(), "User registered successfully.");
    }

    @PostMapping("/login")
    public Result<TokenResponse> authenticate(@RequestBody LoginRequest request) {
        try {
            var authenticatedUser = authenticationService.authenticate(request);
            Map<String,Object> customClaims = new HashMap<>();
            customClaims.put("GivenName",authenticatedUser.getFirstName());
            customClaims.put("SurName",authenticatedUser.getLastLame());
            String jwtToken = tokenService.generateToken(customClaims,authenticatedUser);
            return Result.Success(TokenResponse.builder().token(jwtToken).expiresIn(tokenService.getExpirationTime()).build(), "Login successful.");
        } catch (Exception e) {
            log.error(e);
            return Result.Fail("Invalid credentials.");
        }
    }
}
