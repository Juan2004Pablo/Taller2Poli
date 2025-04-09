package com.example.demo_2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo_2.Models.Entities.Order;
import com.example.demo_2.Services.DetailService;
import com.example.demo_2.Services.OrderService;
import com.example.demo_2.Services.PayService;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private DetailService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable long id) {
        Order order = orderService.createOrder(id, cartService.getTotalAmount(id));
        payService.createPay(order.getIdOrder());

        return "redirect:/shopping-cart";
    }
}
