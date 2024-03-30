package com.example.todoapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Table(name = "TodoItems")
@Entity
@Getter
@NoArgsConstructor
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String details;
    @Column(nullable = true)
    private String description;
    private Timestamp createdDate;
    @Column(nullable = true)
    private Timestamp completedDate;
    private TodoStatus status;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private TodoUser owner;
    public TodoItem(String details, String description,TodoUser owner) {
        this.details = details;
        this.description = description;
        this.createdDate = new Timestamp(System.currentTimeMillis());
        this.status = TodoStatus.PENDING;
        this.owner = owner;
    }
    public  void markComplete(){
        this.status = TodoStatus.COMPLETED;
        this.completedDate = new Timestamp(System.currentTimeMillis());
    }
}
