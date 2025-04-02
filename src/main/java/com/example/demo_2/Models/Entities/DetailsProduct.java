package com.example.demo_2.Models.Entities;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Details_products")
public class DetailsProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetailsProducts")
    private Integer idDetailsProducts;

    @ManyToOne
    @JoinColumn(name = "idDetail", nullable = false)
    private Detail detail;

    @ManyToOne
    @JoinColumn(name = "idProduct", nullable = false)
    private Product product;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
}