package com.example.demo_2.Repository;

import com.example.demo_2.Models.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryIdCategory(Integer idCategory);
}