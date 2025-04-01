package com.example.demo_2.Repository;

import com.example.demo_2.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}