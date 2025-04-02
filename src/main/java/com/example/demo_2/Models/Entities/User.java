package com.example.demo_2.Models.Entities;

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

@Data
@Entity
@Table(name = "Users")
public class User implements UserDetails {
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
    
    @Column(name = "Role", nullable = false)
    private String role;
    
    @Column(name = "Created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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
    }
}