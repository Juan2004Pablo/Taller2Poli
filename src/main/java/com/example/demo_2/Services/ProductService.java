package com.example.demo_2.Services;

import com.example.demo_2.Models.Entities.Product;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product product);
    void delete(Long id);
    List<Product> findByCategory(Integer categoryId);
}