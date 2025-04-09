package com.example.demo_2.Repository;

import com.example.demo_2.Models.Entities.Detail;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

@Transactional
public interface DetailRepository extends JpaRepository<Detail, Long> {
    @Query("SELECT d FROM Detail d WHERE d.status = 'ACTIVE' ORDER BY d.createAt DESC")
    Optional<Detail> findLatestActiveDetail();

    Optional<Detail> findTopByStatusOrderByIdDetailDesc(String status);
}
