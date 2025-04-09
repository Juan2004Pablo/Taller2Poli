package com.example.demo_2.Services.impl;

import java.time.LocalDateTime;

import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.Order;
import com.example.demo_2.Models.Entities.User;
import com.example.demo_2.Repository.DetailRepository;
import com.example.demo_2.Repository.OrderRepository;
import com.example.demo_2.Repository.UserRepository;
import com.example.demo_2.Services.OrderService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private DetailRepository detailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Long idDetail, Long total) {
        Detail detail = detailRepository.findById(idDetail)
                .orElseThrow(() -> new EntityNotFoundException("Detalle no encontrado con ID: " + idDetail));

        if (total == null || total < 0) {
            throw new IllegalArgumentException("El total no puede ser nulo o negativo.");
        }

        if (detail.getOrder() != null) {
            Order existingOrder = detail.getOrder();
            existingOrder.setTotal(total);
            existingOrder.setCreateAt(LocalDateTime.now());
            return orderRepository.save(existingOrder);
        }

        Long userId = 1L;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + userId));

        Order order = new Order();
        order.setIdDetail(idDetail);
        order.setAddress(user.getAddress());
        order.setTotal(total);
        order.setDetail(detail);
        order.setCreateAt(LocalDateTime.now());

        return orderRepository.save(order);
    }
}
