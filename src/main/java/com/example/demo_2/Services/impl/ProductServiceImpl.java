package com.example.demo_2.Services.impl;

import com.example.demo_2.Models.Entities.Product;
import com.example.demo_2.Repository.ProductRepository;
import com.example.demo_2.Services.ProductService;

//import com.ecommerce.demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByCategory(Integer categoryId) {
        return productRepository.findByCategoryIdCategory(categoryId);
    }
}