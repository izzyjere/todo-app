package com.example.todoapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Table(name = "TodoItems")
@Entity
@Getter
@NoArgsConstructor
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private int id;
    @Setter
    private String details;
    @Column(nullable = true)
    @Setter
    private String description;
    private Timestamp createdDate;
    @Column(nullable = true)
    private Timestamp completedDate;
    private TodoStatus status;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private TodoUser owner;
    @Setter
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
    @Setter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
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
