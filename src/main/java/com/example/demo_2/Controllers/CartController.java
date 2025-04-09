package com.example.demo_2.Controllers;

import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.Product;
import com.example.demo_2.Services.ProductService;
import com.example.demo_2.Services.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shopping-cart")
public class CartController {

    @Autowired
    private DetailService cartService;

    @Autowired
    protected ProductService productService;

    @GetMapping
    public String showCart(Model model) {
        Detail cart = cartService.getLatestActiveDetail();
        Long productCount = cartService.countProductsInDetail(cart.getIdDetail());

        Long totalAmount = cartService.getTotalAmount(cart.getIdDetail());
        model.addAttribute("cartDetail", cart);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("productCount", productCount);

        return "ShoppingCart/index";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long productId, @RequestParam("quantity") int quantity) {
        Product product = productService.findById(productId);
        if (product == null) {
            return "redirect:/shopping-cart?error=ProductNotFound";
        }

        Detail detail = cartService.getLatestActiveDetail();

        if (detail == null) {
            detail = new Detail();
            cartService.store(detail);
        }
        cartService.addProductToCart(detail.getIdDetail(), productId, quantity);
        return "redirect:/shopping-cart";
    }

    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        Detail detail = cartService.getLatestActiveDetail();

        if (detail == null) {
            throw new RuntimeException("No se encontró un detalle activo");
        }

        cartService.removeProductFromDetail(id, detail.getIdDetail());
        return "redirect:/shopping-cart";
    }

    @PostMapping("/update-quantity/{id}")
    public String updateQuantity(@PathVariable int id,
            @RequestParam int quantity) {
        cartService.updateQuantityProduct(id, quantity);
        return "redirect:/shopping-cart";
    }
}
