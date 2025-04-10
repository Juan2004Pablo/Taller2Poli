package com.example.demo_2.Controllers;


import com.example.demo_2.Models.Entities.Category;
import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.Product;
import com.example.demo_2.Services.CategoryService;
import com.example.demo_2.Services.DetailService;
import com.example.demo_2.Services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    private DetailService cartService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listProducts(Model model) {
        Detail cart = cartService.getLatestActiveDetail();
        Long productCount = cartService.countProductsInDetail(cart.getIdDetail());

        model.addAttribute("products", productService.findAll());
        model.addAttribute("productCount", productCount);
        return "products/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.findById(id));
        return "Products/show";
    }

     @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "products/create";
    }

   @PostMapping("/save")
   public String saveProduct(
    @ModelAttribute("product") Product product,
    BindingResult result,
    Model model) {
    
    if (result.hasErrors()) {
        model.addAttribute("categories", categoryService.findAll());
        return "products/create";
    }
    
    // Solo necesitamos el ID para la relación
    if (product.getCategory() != null && product.getCategory().getIdCategory() != null) {
        Category category = categoryService.findById(product.getCategory().getIdCategory());
        product.setCategory(category);
    }
    
    productService.save(product);
    return "redirect:/products";
}

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute Product product, 
                              BindingResult result) {
        if (result.hasErrors()) {
            return "products/edit";
        }
        product.setIdProduct(id);
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/category/{id}")
    public String listByCategory(@PathVariable Integer id, Model model) {
        List<Product> products = productService.findByCategory(id);
        Category category = categoryService.findById(id);
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        return "products/list-by-category";
    }
}