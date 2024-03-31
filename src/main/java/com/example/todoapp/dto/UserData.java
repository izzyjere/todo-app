package com.example.todoapp.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class UserData {
   private String username;
   private String fullName;
   private int id;
}
