package com.example.todoapp.controllers;

import com.example.todoapp.dto.CreateUserRequest;
import com.example.todoapp.dto.LoginRequest;
import com.example.todoapp.dto.LoginResponse;
import com.example.todoapp.dto.Result;
import com.example.todoapp.dto.TokenResponse;
import com.example.todoapp.dto.UserData;
import com.example.todoapp.security.services.AuthenticationService;
import com.example.todoapp.security.services.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Log4j2
@CrossOrigin("*")
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
    public Result<LoginResponse> authenticate(@RequestBody LoginRequest request) {
        try {
            var authenticatedUser = authenticationService.authenticate(request);
            Map<String, Object> customClaims = new HashMap<>();
            customClaims.put("GivenName", authenticatedUser.getFirstName());
            customClaims.put("Surname", authenticatedUser.getLastLame());
            customClaims.put("Id", authenticatedUser.getId());
            String jwtToken = tokenService.generateToken(customClaims, authenticatedUser);
            var tokenData = TokenResponse.builder()
                                         .token(jwtToken)
                                         .expiresIn(tokenService.getExpirationTime()).build();

            var userData = UserData.builder()
                                   .fullName(authenticatedUser.toString())
                                   .id(authenticatedUser.getId())
                                   .username(authenticatedUser.getUsername()).build();
            var response = LoginResponse.builder().tokenData(tokenData).userData(userData)
                    .build();
            return Result.Success(response, "Login successful.");
        } catch (Exception e) {
            log.error(e);
            return Result.Fail("Invalid credentials.");
        }
    }
}
