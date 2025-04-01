package com.example.demo_2.Models.DAO.Product;

import com.example.demo_2.Models.Entities.Products;
import java.util.List;
//import java.util.Optional;

public interface IProductDao {
    List<Products> findAll();
    Products findById(Long id);
    void save(Products product);
    void update(Products product);
    void delete(Long id);
    List<Products> findByName(String name);
}