package com.example.demo_2.Controllers;

import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.DetailProduct;
import com.example.demo_2.Models.Entities.Product;
import com.example.demo_2.Models.Entities.User;
import com.example.demo_2.Models.Services.Cart.CartService;
import com.example.demo_2.Models.Services.Product.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shopping-cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    protected IProductService productService;

    @GetMapping
    public String showCart(Model model) {
        Detail cartDetail = cartService.getCartDetailById();

        // Calcular el total de la compra
        Long totalAmount = cartService.getTotalAmount(cartDetail.getId());

        model.addAttribute("cartDetail", cartDetail);
        model.addAttribute("totalAmount", totalAmount);

        return "ShoppingCart/index";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long productId,
            @RequestParam("quantity") int quantity) {
        // Obtener el usuario autenticado (si aplica)
        // User user = new User();
        // quantity = 3;

        // Buscar el producto en la base de datos
        Product product = productService.show(productId);
        if (product == null) {
            return "redirect:/shopping-cart?error=ProductNotFound";
        }

        // Buscar el detail asociado y activo del usuario, o crear uno nuevo si no
        // existe
        Detail detail = cartService.show(1L); // Cambiar 1L por el ID real del carrito
        if (detail == null) {
            detail = new Detail();
            // detail.setUser(user); // Asociar al usuario si aplica
            cartService.store(detail);
        }

        // Agregar el producto al carrito con cantidad 1
        cartService.addProductToCart(detail.getId(), productId, quantity);

        return "redirect:/shopping-cart";
    }

    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "redirect:/shopping-cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/shopping-cart";
    }
}