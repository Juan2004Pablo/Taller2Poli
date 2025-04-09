package com.example.demo_2.Services;

<<<<<<< HEAD
import com.example.demo_2.Models.Entities.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void delete(Long id);
=======
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo_2.Models.Entities.User;
import com.example.demo_2.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
>>>>>>> login-signup-feature
}