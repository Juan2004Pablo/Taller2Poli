package com.example.demo_2.Models.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Details_products")
public class DetailsProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetailsProducts")
    private Integer idDetailsProducts;

    @ManyToOne
    @JoinColumn(name = "idDetail", referencedColumnName = "idDetail")
    private Details details;

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "idProduct")
    private Products product;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    // Getters y Setters
    public Integer getIdDetailsProducts() {
        return idDetailsProducts;
    }

    public void setIdDetailsProducts(Integer idDetailsProducts) {
        this.idDetailsProducts = idDetailsProducts;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
