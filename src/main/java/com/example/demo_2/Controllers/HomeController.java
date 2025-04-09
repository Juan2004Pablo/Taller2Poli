package com.example.demo_2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Services.DetailService;
import com.example.demo_2.Services.ProductService;

@Controller
public class HomeController {
    @Autowired
    protected ProductService productService;

    @Autowired
    private DetailService cartService;

    @GetMapping("/")
    public String index(Model model) {
        Detail cart = cartService.getLatestActiveDetail();
        Long productCount = cartService.countProductsInDetail(cart.getIdDetail());

        model.addAttribute("products", productService.findAll());
        model.addAttribute("productCount", productCount);

        return "HomePage/home";
    }

    @GetMapping("/home/show/{id}")
    public String show(@PathVariable Long id, Model model) {
        Detail cart = cartService.getLatestActiveDetail();
        Long productCount = cartService.countProductsInDetail(cart.getIdDetail());
        
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("productCount", productCount);

        return "HomePage/show";
    }
}