package com.example.demo_2.Services.impl;

import com.example.demo_2.Models.Entities.User;
import com.example.demo_2.Repository.UserRepository;
import com.example.demo_2.Services.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import com.ecommerce.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Get the currently authenticated user
     * @return Optional containing the User if authenticated, or empty if not
     */
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }
        
        if (authentication.getPrincipal() instanceof User) {
            return Optional.of((User) authentication.getPrincipal());
        } else if (authentication.getName() != null) {
            // If it's not a User object but we have the username (email)
            return userRepository.findByEmail(authentication.getName());
        }
        
        return Optional.empty();
    }

    /**
     * Get the ID of the currently authenticated user
     * @return User ID if authenticated, or null if not
     */
    public Long getCurrentUserId() {
        return getCurrentUser().map(User::getIdentification).orElse(null);
    }

    /**
     * Check if a user is currently authenticated
     * @return true if authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        return getCurrentUser().isPresent();
    }
}