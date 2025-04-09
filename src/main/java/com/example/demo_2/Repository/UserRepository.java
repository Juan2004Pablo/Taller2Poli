package com.example.demo_2.Repository;

<<<<<<< HEAD
import com.example.demo_2.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
=======
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_2.Models.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
>>>>>>> login-signup-feature
}