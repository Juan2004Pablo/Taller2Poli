package com.example.demo_2.Repository;

import com.example.demo_2.Models.Entities.DetailsProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsProductRepository extends JpaRepository<DetailsProduct, Integer> {
}