package com.example.demo_2.Models.Services.Product;

import com.example.demo_2.Models.Entities.Category;
import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> findAll();
    Optional<Category> findById(Integer id);
    void save(Category category);
    void update(Category category);
    void delete(Integer id);
    boolean existsByName(String name);
}