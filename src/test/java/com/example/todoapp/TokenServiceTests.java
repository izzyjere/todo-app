package com.example.todoapp;

import com.example.todoapp.security.services.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TokenServiceTests {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String TEST_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJHaXZlbk5hbWUiOiJXaXNkb20iLCJJZCI6MSwiU3VybmFtZSI6IkplcmUiLCJzdWIiOiJ3aXNkb20iLCJpYXQiOjE3MTE4MTQ1NjUsImV4cCI6MTcxMTgxODE2NX0.lQorxNdfT4ODanHnMn0Gx_L6UEV9nf0NLpkMEkshdeZandbKfk597SpY1gB982zTTIJT9RyTu6PQ1KfCVQ1H7m8PJj1IX94rGYhIutBQm1WtExX3Dz-KVeFvFi6qV2P_AUdsjbVTBdynJLIfPaIsi3P9lMz1oj89pSjqZFuj-cyc1HkfReikm-Dv7CDFQUte7mnijN2UIo4YzpbwtOvzfHzHn4-1a_f9DS_-cJ4jkRZB_gZCa_Ef732Osi87oiM7_74OJBy5w03GhH0Co20jyCJ1AfjuicouaSx_EHLeswVuj7Kkde1QOhLiIksm1gwsSWYZNwDiXvUHEAQHnVsL1Q";

    @Test
    public void testExtractUsername() {
        String username = "wisdom";
        assertEquals(username, tokenService.extractUsername(TEST_TOKEN));
    }

    @Test
    public void testGenerateToken() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("GivenName", "Wisdom");
        extraClaims.put("Surname", "Jere");
        extraClaims.put("Id", 1);
        UserDetails userDetails = User.withUsername("wisdom").password(passwordEncoder.encode("test1234")).build();
        String token = tokenService.generateToken(extraClaims, userDetails);
        assertNotNull(token);
    }

    @Test
    public void testGetExpirationTime() {
        long expirationTime = tokenService.getExpirationTime();
        // Ensure expiration time is a positive value
        assertTrue(expirationTime > 0);
    }
}