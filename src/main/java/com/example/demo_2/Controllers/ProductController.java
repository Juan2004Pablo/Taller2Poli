package com.example.demo_2.Controllers;


import com.example.demo_2.Models.Entities.Products;
import com.example.demo_2.Models.Services.Product.IProductService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    private final IProductService productService;

    //@Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("products", productService.findAll());
        return "Products/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Products product = productService.findById(id);            
        model.addAttribute("product", product);
        return "Products/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Products());
        return "Products/create";
    }

    @PostMapping("/store")
    public String store(@ModelAttribute Products product, RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("success", "Product created successfully!");
        return "redirect:/products/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Products product = productService.findById(id);
        model.addAttribute("product", product);
        return "Products/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Products product, RedirectAttributes redirectAttributes) {
        product.setIdProduct(id);
        productService.update(product);
        redirectAttributes.addFlashAttribute("success", "Product updated successfully!");
        return "redirect:/products/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Product deleted successfully!");
        return "redirect:/products/index";
    }
}