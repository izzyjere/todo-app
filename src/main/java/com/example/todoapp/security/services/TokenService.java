package com.example.todoapp.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TokenService {

    String extractUsername(String token);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    long getExpirationTime();
    boolean isTokenValid(String token, UserDetails userDetails);
}
