package com.example.demo_2.Models.Entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "pays")
public class Pay implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, unique = true)
    private String reference;

    @Enumerated(EnumType.STRING)
    // La definición de la columna con valores y valor por defecto se puede configurar en la base de datos
    @Column(columnDefinition = "enum('PENDING', 'APPROVED', 'REJECTED') default 'PENDING'")
    private Status status;

    @Column(name = "requestId", unique = true)
    private Long requestId;

    @Column(name = "process_url")
    private String processUrl;

    private String name;

    @Column(name = "document_type")
    private String documentType;

    private Long document;
    private String email;
    private Long phone;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    public Pay() {
    }

    public Pay(Long id, String reference, Status status, Long requestId, String processUrl) {
        this.id = id;
        this.reference = reference;
        this.status = status;
        this.requestId = requestId;
        this.processUrl = processUrl;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Long getRequestId() {
        return requestId;
    }
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
    public String getProcessUrl() {
        return processUrl;
    }
    public void setProcessUrl(String processUrl) {
        this.processUrl = processUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDocumentType() {
        return documentType;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    public Long getDocument() {
        return document;
    }
    public void setDocument(Long document) {
        this.document = document;
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
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    // Enum para el estado del pago
    public enum Status {
        PENDING, APPROVED, REJECTED
    }
}