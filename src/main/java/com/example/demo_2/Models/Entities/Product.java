package com.example.demo_2.Models.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduct")
    private Long idProduct;

    @Column(name = "idCategory")
    private Integer idCategory;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Image", nullable = false)
    private String image;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @Column(name = "Price", nullable = false)
    private Long price;

    @Column(name = "Created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.createdAt = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "idCategory", insertable = false, updatable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<DetailsProduct> detailsProducts;
}