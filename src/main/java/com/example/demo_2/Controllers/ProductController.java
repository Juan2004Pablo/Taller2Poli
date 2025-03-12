package com.example.demo_2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo_2.Models.Entities.Product;
import com.example.demo_2.Models.Services.Product.IProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    protected IProductService productService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("products", productService.index());
        return "Products/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.show(id));
        return "Products/show";
    }

    @GetMapping("/create")
    public String create() {
        return "Products/create";
    }

    @PostMapping("/store")
    public String store(@ModelAttribute Product product) {
        productService.store(product);
        return "redirect:/products/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.show(id));
        return "Products/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Product product) {
        productService.update(id, product);
        return "redirect:/products/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products/index";
    }
}
