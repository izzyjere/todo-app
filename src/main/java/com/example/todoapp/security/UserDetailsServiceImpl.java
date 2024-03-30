package com.example.todoapp.security;

import com.example.todoapp.services.TodoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TodoUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userService.findByName(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User was not found.");
        }
        var user = optionalUser.get();
        return new User(user.getUsername(),user.getPassword(), Collections.emptyList());
    }
}
