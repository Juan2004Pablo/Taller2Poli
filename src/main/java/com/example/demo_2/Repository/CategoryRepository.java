package com.example.demo_2.Repository;

import com.example.demo_2.Models.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}