package com.example.demo_2.Repository;

import com.example.demo_2.Models.Entities.Pay;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PayRepository extends JpaRepository<Pay, Integer> {
}