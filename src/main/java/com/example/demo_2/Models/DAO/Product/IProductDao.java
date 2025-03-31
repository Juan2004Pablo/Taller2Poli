package com.example.demo_2.Models.DAO.Product;

import com.example.demo_2.Models.Entities.Product;
import java.util.List;
import java.util.Optional;

public interface IProductDao {
    List<Product> findAll();
    Product findById(Long id);
    void save(Product product);
    void update(Product product);
    void delete(Long id);
    List<Product> findByName(String name);
}