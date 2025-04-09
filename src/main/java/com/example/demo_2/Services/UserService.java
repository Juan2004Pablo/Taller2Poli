package com.example.demo_2.Services;

import com.example.demo_2.Models.Entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void delete(Long id);
    Optional<User> getCurrentUser();
    Long getCurrentUserId();
    boolean isAuthenticated();
}