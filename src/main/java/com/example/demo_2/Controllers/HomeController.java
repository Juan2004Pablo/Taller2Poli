package com.example.demo_2.Controllers;

import com.example.demo_2.Models.Services.Product.IProductService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    
    private final IProductService productService;

  //  @Autowired
    public HomeController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.findAll());
        return "HomePage/home";
    }

    @GetMapping("/home/show/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));            
        return "HomePage/show";
    }
}