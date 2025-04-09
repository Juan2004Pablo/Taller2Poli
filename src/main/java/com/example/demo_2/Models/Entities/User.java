package com.example.demo_2.Models.Entities;

<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
//import java.util.List;
=======
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
>>>>>>> login-signup-feature

@Data
@Entity
@Table(name = "Users")
<<<<<<< HEAD
public class User {
=======
public class User implements UserDetails {
>>>>>>> login-signup-feature
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
    
<<<<<<< HEAD
=======
    @Column(name = "Role", nullable = false)
    private String role;
    
>>>>>>> login-signup-feature
    @Column(name = "Created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
<<<<<<< HEAD
=======
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
>>>>>>> login-signup-feature
    }
}