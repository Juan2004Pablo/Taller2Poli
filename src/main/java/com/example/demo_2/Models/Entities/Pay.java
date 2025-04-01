package com.example.demo_2.Models.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Pays")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPay")
    private Integer idPay;

    @Column(name = "Reference", nullable = false, unique = true)
    private String reference;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "RequestId", nullable = false, unique = true)
    private Long requestId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Identification", nullable = false)
    private Long identification;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Phone")
    private Long phone;

    @Column(name = "Payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "Create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "idOrder")
    private Long idOrder;

    @ManyToOne
    @JoinColumn(name = "idOrder", insertable = false, updatable = false)
    private Order order;
}