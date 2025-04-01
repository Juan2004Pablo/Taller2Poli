package com.example.demo_2.Repository;

import com.example.demo_2.Models.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}