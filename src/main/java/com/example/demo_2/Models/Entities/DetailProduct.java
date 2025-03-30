package com.example.demo_2.Models.Entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "detail_products")
public class DetailProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "detail_id", nullable = false)
    private Detail detail;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity; // Cantidad del producto en el carrito

    public DetailProduct() {}

    public DetailProduct(Detail detail, Product product, int quantity) {
        this.detail = detail;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
