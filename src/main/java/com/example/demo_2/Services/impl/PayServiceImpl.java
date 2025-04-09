package com.example.demo_2.Services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.demo_2.Helpers.DateHelper;
import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.Order;
import com.example.demo_2.Models.Entities.Pay;
import com.example.demo_2.Models.Entities.User;
import com.example.demo_2.Repository.OrderRepository;
import com.example.demo_2.Repository.PayRepository;
import com.example.demo_2.Repository.UserRepository;
import com.example.demo_2.Services.PayService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PayRepository payRepository;

    @Transactional
    public Pay createPay(Long idOrder) {
        Optional<Order> optionalOrder = orderRepository.findById(idOrder);
        if (optionalOrder.isEmpty()) {
            throw new EntityNotFoundException("No se encontró la orden con id: " + idOrder);
        }

        Optional<Pay> existingPay = payRepository.findByIdOrder(idOrder);
        if (existingPay.isPresent()) {
            return existingPay.get();
        }

        Order order = optionalOrder.get();

        Detail detail = order.getDetail();
        if (detail == null) {
            throw new EntityNotFoundException("No se encontró el detalle para la orden con id: " + idOrder);
        }

        Optional<User> optionalUser = userRepository.findById(detail.getIdentification());
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException(
                    "No se encontró el usuario con identificación: " + detail.getIdentification());
        }

        User user = optionalUser.get();

        Pay pay = new Pay();
        pay.setReference(DateHelper.generateTestString());
        pay.setStatus("PENDING");
        pay.setRequestId(System.currentTimeMillis());
        pay.setName(user.getName());
        pay.setIdentification(user.getIdentification());
        pay.setEmail(user.getEmail());
        pay.setPhone(user.getPhone() != null ? Long.parseLong(user.getPhone()) : null);
        pay.setPaymentMethod("");
        pay.setCreateAt(LocalDateTime.now());
        pay.setIdOrder(idOrder);

        return payRepository.save(pay);
    }

    @Override
    @Transactional
    public Pay updatePay(Pay pay) {
        if (pay.getIdPay() == null) {
            throw new IllegalArgumentException("El ID del pago no puede ser nulo para actualizar.");
        }

        Optional<Pay> existingPayOpt = payRepository.findById(pay.getIdPay());

        if (existingPayOpt.isEmpty()) {
            throw new EntityNotFoundException("No se encontró el pago con ID: " + pay.getIdPay());
        }

        if (pay.getRequestId() != null) {
            Optional<Pay> payWithSameRequestId = payRepository.findByRequestId(pay.getRequestId());
    
            if (payWithSameRequestId.isPresent() && !payWithSameRequestId.get().getIdPay().equals(pay.getIdPay())) {
                throw new IllegalArgumentException("Ya existe un pago con el mismo requestId: " + pay.getRequestId());
            }
        }

        return payRepository.save(pay);
    }
}
