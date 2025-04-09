package com.example.demo_2.Controllers;


import com.example.demo_2.Models.Entities.Category;
import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Services.CategoryService;
import com.example.demo_2.Services.DetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private DetailService cartService;

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        Long cartId = cartService.getLatestActiveDetailId();
        Detail cart = cartService.getCartDetailById(cartId);
        Long productCount = cartService.countProductsInDetail(cart.getIdDetail());

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("productCount", productCount);

        return "categories/list";
    }
    // Muestra el formulario de creación
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/create";
    }

  
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/categories"; // Redirige al listado después de guardar
    }


     // Muestra el formulario de edición (GET)
     @GetMapping("/edit/{id}")
     public String showEditForm(@PathVariable Integer id, Model model) {
         Category category = categoryService.findById(id);
         model.addAttribute("category", category);
         return "categories/edit"; // o "categories/form" si usas el mismo template
     }
 
     // Procesa el formulario de edición (POST)
     @PostMapping("/update/{id}")
     public String updateCategory(@PathVariable Integer id, 
                                @ModelAttribute Category category) {
         category.setIdCategory(id);
         categoryService.save(category);
         return "redirect:/categories";
     }

     
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}