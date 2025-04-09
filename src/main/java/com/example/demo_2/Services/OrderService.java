package com.example.demo_2.Services;

import com.example.demo_2.Models.Entities.Order;

public interface OrderService {
    Order createOrder(Long idDetail, Long total);
}
