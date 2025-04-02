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
        // Falta consultar el detalle con estado activo del usuario
        Long cartId = cartService.getLatestActiveDetailId();
        Detail cart = cartService.getCartDetailById(cartId);
        Long productCount = cartService.countProductsInDetail(cart.getIdDetail());

        Long totalAmount = cartService.getTotalAmount(cart.getIdDetail());
        model.addAttribute("cartDetail", cart);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("productCount", productCount);

        return "ShoppingCart/index";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long productId, @RequestParam("quantity") int quantity) {
        // Buscar el producto en la base de datos
        Product product = productService.findById(productId);
        if (product == null) {
            return "redirect:/shopping-cart?error=ProductNotFound";
        }
        // Buscar o crear el detail (carrito) usando el repositorio
        Long cartId = cartService.getLatestActiveDetailId();
        Detail detail = cartService.getCartDetailById(cartId);

        if (detail == null) {
            detail = new Detail();
            // Asignar datos necesarios (p.ej. usuario, fecha, estado)
            cartService.store(detail);
        }
        // Agregar el producto al carrito con la cantidad indicada
        cartService.addProductToCart(detail.getIdDetail(), productId, quantity);
        return "redirect:/shopping-cart";
    }

    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        Long cartId = cartService.getLatestActiveDetailId();
        Detail latestDetail = cartService.getCartDetailById(cartId);
    
        if (latestDetail == null) {
            throw new RuntimeException("No se encontró un detalle activo");
        }

        cartService.removeProductFromDetail(id, latestDetail.getIdDetail());
        return "redirect:/shopping-cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/shopping-cart";
    }
}
