package com.example.demo_2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo_2.Api.Response.CreateSessionResponse;
import com.example.demo_2.Models.Entities.Order;
import com.example.demo_2.Models.Entities.Pay;
import com.example.demo_2.Services.DetailService;
import com.example.demo_2.Services.OrderService;
import com.example.demo_2.Services.PayService;
import com.example.demo_2.Services.Api.PlaceToPayService;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private DetailService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Autowired
    private PlaceToPayService placeToPayService;

    @PostMapping("/create/session/{id}")
    public String createSession(@PathVariable long id) {
        try {
            Order order = orderService.createOrder(id, cartService.getTotalAmount(id));
            Pay pay = payService.createPay(order.getIdOrder());
            CreateSessionResponse sessionResponse = placeToPayService.createSession(order);

            pay.setRequestId(sessionResponse.getRequestId());
            payService.updatePay(pay);

            return "redirect:" + sessionResponse.getProcessUrl();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear la sesión con PlaceToPay: " + e.getMessage());
        }
    }
}
