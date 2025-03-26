package com.example.demo_2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo_2.Models.Services.Product.IProductService;

@Controller
public class HomeController {
    @Autowired
    protected IProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.index());
        return "HomePage/home";
    }

    @GetMapping("/home/show/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.show(id));
        return "HomePage/show";
    }
}
