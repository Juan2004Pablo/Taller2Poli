package com.example.demo_2.Services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.demo_2.Api.Response.QuerySessionResponse;
import com.example.demo_2.Helpers.DateHelper;
import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.DetailsProduct;
import com.example.demo_2.Models.Entities.Order;
import com.example.demo_2.Models.Entities.Pay;
import com.example.demo_2.Models.Entities.Product;
import com.example.demo_2.Models.Entities.User;
import com.example.demo_2.Repository.OrderRepository;
import com.example.demo_2.Repository.PayRepository;
import com.example.demo_2.Repository.UserRepository;
import com.example.demo_2.Services.PayService;
import com.example.demo_2.Services.Api.PlaceToPayService;

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

    @Autowired
    private PlaceToPayService placeToPayService;

    @Transactional
    public Pay createPay(Long idOrder) {
        Order order = orderRepository.findById(idOrder)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la orden con id: " + idOrder));

        if (order.getDetail() == null) {
            throw new EntityNotFoundException("No se encontró el detalle para la orden con id: " + idOrder);
        }

        Optional<Pay> existingPay = payRepository.findByIdOrder(idOrder);
        if (existingPay.isPresent()) {
            return existingPay.get();
        }

        Detail detail = order.getDetail();
        if (detail == null) {
            throw new EntityNotFoundException("No se encontró el detalle para la orden con id: " + idOrder);
        }

        Optional<User> optionalUser = userRepository.findById(detail.getIdentification());
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException(
                    "No se encontró el usuario con identificación: " + detail.getIdentification());
        }

        Pay pay = payRepository.findByIdOrder(idOrder).orElse(null);
        if (pay != null) {
            return pay;
        }

        User user = userRepository.findById(order.getDetail().getIdentification())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el usuario con identificación: " + order.getDetail().getIdentification()));

        pay = new Pay();
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

    @Override
    @Transactional
    public void updatePendingPays() {
        List<Pay> pendingPays = payRepository.findByStatus("PENDING");

        System.out.println("🔍 Pagos en estado PENDING encontrados: " + pendingPays.size());

        for (Pay pay : pendingPays) {
            try {
                QuerySessionResponse response = placeToPayService.querySession(pay.getRequestId());

                String newStatus = response.getStatus().getStatus();
                if (!pay.getStatus().equals(newStatus)) {
                    pay.setStatus(newStatus);
                    pay.setCreateAt(LocalDateTime.now());
                    payRepository.save(pay);
                }

                if (pay.getOrder() != null && pay.getOrder().getDetail() != null) {
                    Detail detail = pay.getOrder().getDetail();

                    if (!detail.getStatus().equals("INACTIVE")) {
                        detail.setStatus("INACTIVE");
                        System.out.println("🔄 Detalle con ID " + detail.getIdDetail() + " cambiado a INACTIVE.");
                    }

                    if ("APPROVED".equalsIgnoreCase(newStatus)) {
                        for (DetailsProduct dp : detail.getDetailsProducts()) {
                            Product product = dp.getProduct();
                            Integer oldStock = product.getStock();
                            Integer newStock = oldStock - dp.getQuantity();

                            if (newStock < 0) {
                                System.err.println("Stock insuficiente para el producto " + product.getName());
                                continue;
                            }

                            product.setStock(newStock);
                            System.out.println("Producto: " + product.getName() + " stock actualizado de " + oldStock
                                    + " a " + newStock);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error actualizando el estado del pago con requestId " + pay.getRequestId() + ": "
                        + e.getMessage());
            }
        }
    }
}
