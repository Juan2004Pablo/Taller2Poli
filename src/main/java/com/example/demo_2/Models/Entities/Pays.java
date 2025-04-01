package com.example.demo_2.Models.Entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Pays")
public class Pays {

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "idOrder", referencedColumnName = "idOrder")
    private Orders orders;

    // Getters y Setters
    public Integer getIdPay() {
        return idPay;
    }

    public void setIdPay(Integer idPay) {
        this.idPay = idPay;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdentification() {
        return identification;
    }

    public void setIdentification(Long identification) {
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
