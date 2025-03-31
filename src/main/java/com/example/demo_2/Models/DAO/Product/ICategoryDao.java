package com.example.demo_2.Models.DAO.Product;

import com.example.demo_2.Models.Entities.Category;
import java.util.List;
import java.util.Optional;

public interface ICategoryDao {
    void save(Category category);
    Optional<Category> findById(Integer id);
    List<Category> findAll();
    void update(Category category);
    void delete(Integer id);
    boolean existsByName(String name);
}