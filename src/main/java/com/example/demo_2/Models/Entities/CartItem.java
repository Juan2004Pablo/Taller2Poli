package com.example.demo_2.Models.Entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private Long unitPrice; // Nuevo campo para almacenar el precio unitario

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // Relación con la orden de pago
    private Order order;

    public CartItem() {}

    public CartItem(Product product, int quantity, Long unitPrice, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.order = order;
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Long getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Long unitPrice) { this.unitPrice = unitPrice; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    private static final long serialVersionUID = 1L;
}
