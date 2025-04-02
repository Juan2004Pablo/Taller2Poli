package com.example.demo_2.Repository;

import com.example.demo_2.Models.Entities.DetailsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DetailsProductRepository extends JpaRepository<DetailsProduct, Integer> {
    Optional<DetailsProduct> findByProduct_IdProductAndDetail_IdDetail(Long productId, Long detailId);

    @Query("SELECT COUNT(dp) FROM DetailsProduct dp WHERE dp.detail.idDetail = :detailId")
    Long countProductsInDetail(@Param("detailId") Long detailId);
}