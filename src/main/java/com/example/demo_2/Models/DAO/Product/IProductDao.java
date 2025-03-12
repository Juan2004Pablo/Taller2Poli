package com.example.demo_2.Models.DAO.Product;

import java.util.List;

import com.example.demo_2.Models.Entities.Product;

public interface IProductDao {
    public List<Product> index();
    public Product show(Long id);
    public void store(Product product);
    public void update(Product product);
    public void delete(Long id);
}
