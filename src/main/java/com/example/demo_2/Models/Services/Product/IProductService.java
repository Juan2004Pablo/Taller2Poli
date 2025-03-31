package com.example.demo_2.Models.Services.Product;

import com.example.demo_2.Models.Entities.Product;
import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> findAll();
    Product findById(Long id);  // Aquí el tipo de retorno debe ser Product
    void save(Product product);
    void update(Product product);
    void delete(Long id);
    List<Product> findByName(String name);
}
