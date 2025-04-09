package com.example.demo_2.Repository;

import com.example.demo_2.Models.Entities.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay, Integer> {
    Optional<Pay> findByIdOrder(Long idOrder);
    Optional<Pay> findByRequestId(Long requestId);
}