package com.example.demo_2.Services;

import com.example.demo_2.Models.Entities.Category;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Integer id);
    Category save(Category category);
    void delete(Integer id);
}