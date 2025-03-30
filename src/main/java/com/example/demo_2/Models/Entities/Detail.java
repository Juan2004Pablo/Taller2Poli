package com.example.demo_2.Models.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "details")
public class Detail implements Serializable {
    public static final String ACTIVE = "active";
    public static final String FINALIZED = "finalized";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "detail", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetailProduct> detailProducts = new HashSet<>();

    // Relación muchos a uno con User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Relación uno a uno con Order (lado inverso)
    @OneToOne(mappedBy = "detail", cascade = CascadeType.ALL)
    private Order order;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        if (this.status == null) {
            this.status = Detail.ACTIVE;
        }
    }    

    public Detail() {
    }

    public Detail(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Set<DetailProduct> getDetailProducts() {
        return detailProducts;
    }

    public void setDetailProducts(Set<DetailProduct> detailProducts) {
        this.detailProducts = detailProducts;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public void addProduct(Product product, int quantity) {
        this.detailProducts.add(new DetailProduct(this, product, quantity));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
