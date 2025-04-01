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

    @Column(name = "idDetail")
    private Long idDetail;

    @Column(name = "idProduct")
    private Long idProduct;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "idDetail", insertable = false, updatable = false)
    private Detail detail;

    @ManyToOne
    @JoinColumn(name = "idProduct", insertable = false, updatable = false)
    private Product product;
}