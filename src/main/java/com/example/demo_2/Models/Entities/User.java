package com.example.demo_2.Models.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
//import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "Identification")
    private Long identification;
    
    @Column(name = "Name", nullable = false)
    private String name;
    
    @Column(name = "Address", nullable = false)
    private String address;
    
    @Column(name = "Phone", nullable = false)
    private String phone;
    
    @Column(name = "Email", nullable = false)
    private String email;
    
    @Column(name = "Password")
    private String password;
    
    @Column(name = "Created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}