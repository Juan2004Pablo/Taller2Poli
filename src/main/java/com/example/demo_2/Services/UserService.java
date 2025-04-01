package com.example.demo_2.Services;

import com.example.demo_2.Models.Entities.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void delete(Long id);
}