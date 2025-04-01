package com.example.demo_2.Models.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Details")
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetail")
    private Long idDetail;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "Create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "Identification", nullable = false)
    private Long identification;

    @ManyToOne
    @JoinColumn(name = "Identification", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "detail")
    private List<DetailsProduct> detailsProducts;

    @OneToOne(mappedBy = "detail")
    private Order order;
}