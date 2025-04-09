package com.example.demo_2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.Pay;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import com.example.demo_2.Repository.PayRepository;
import com.example.demo_2.Services.DetailService;
import com.example.demo_2.Services.PayService;

import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/pays")
public class PayController {
    @Autowired
    private PayRepository payRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private DetailService cartService;

    @GetMapping("")
    public String getPays(Model model) {
        payService.updatePendingPays();
        List<Pay> pays = payRepository.findAll();

        Detail cart = cartService.getLatestActiveDetail();
        Long productCount = cartService.countProductsInDetail(cart.getIdDetail());
        model.addAttribute("productCount", productCount);
        model.addAttribute("pays", pays);
        return "Pays/list";
    }

    @GetMapping("/detail/{id}")
    public String showPaymentDetail(@PathVariable Long id, Model model) {
        Optional<Pay> optionalPay = payRepository.findById(id.intValue());

        if (optionalPay.isEmpty()) {
            throw new EntityNotFoundException("No se encontró el pago con ID: " + id);
        }

        Pay pay = optionalPay.get();
        model.addAttribute("pay", pay);

        return "Pays/paymentDetail";
    }
}
