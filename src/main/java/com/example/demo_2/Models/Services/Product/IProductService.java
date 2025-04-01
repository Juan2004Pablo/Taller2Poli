package com.example.demo_2.Models.Services.Product;

import com.example.demo_2.Models.Entities.Products;
import java.util.List;
//import java.util.Optional;

public interface IProductService {
    List<Products> findAll();
    Products findById(Long id);  // Aquí el tipo de retorno debe ser Product
    void save(Products product);
    void update(Products product);
    void delete(Long id);
    List<Products> findByName(String name);
}
