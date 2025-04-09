package com.example.demo_2.Api;

public class Payment {
    private String reference;
    private String description;
    private Amount amount;

    public Payment(String reference, String description, Amount amount) {
        this.reference = reference;
        this.description = description;
        this.amount = amount;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Amount getAmount() {
        return amount;
    }
    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    
}
