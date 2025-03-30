package com.example.demo_2.Models.Entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long total;
    private String address;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Relación uno a uno con Detail (lado propietario)
    @OneToOne
    @JoinColumn(name = "detail_id")
    private Detail detail;

    // Relación uno a uno inversa con Pay
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Pay pay;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    public Order() {
    }

    public Order(Long id, Long total, String address) {
        this.id = id;
        this.total = total;
        this.address = address;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Detail getDetail() {
        return detail;
    }
    public void setDetail(Detail detail) {
        this.detail = detail;
    }
    public Pay getPay() {
        return pay;
    }
    public void setPay(Pay pay) {
        this.pay = pay;
    }
}