package com.example.demo_2.Api;

public class Amount {
    private String currency = "COP";
    private long total;

    public Amount(long total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
}
