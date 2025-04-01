package com.example.demo_2.Models.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOrder")
    private Long idOrder;

    @Column(name = "Total", nullable = false)
    private Long total;

    @Column(name = "Address")
    private String address;

    @Column(name = "Create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "idDetail")
    private Long idDetail;

    @OneToOne
    @JoinColumn(name = "idDetail", insertable = false, updatable = false)
    private Detail detail;

    @OneToOne(mappedBy = "order")
    private Pay pay;
}