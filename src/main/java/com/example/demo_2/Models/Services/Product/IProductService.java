package com.example.demo_2.Models.Services.Product;

import java.util.List;

import com.example.demo_2.Models.Entities.Product;

public interface IProductService {
    public List<Product> index();
    public Product show(Long id);
    public void store(Product product);
    public void update(Long id, Product product);
    public void delete(Long id);
}
