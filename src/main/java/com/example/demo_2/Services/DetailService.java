package com.example.demo_2.Services;

import java.util.List;
import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.Product;

public interface DetailService {
    void store(Detail detail);
    Detail show(Long id);
    void update(Detail detail);
    
    // Métodos adicionales de la implementación
    List<Detail> getCartDetails();
    Detail getCartDetailById(Long detailId);
    void addProductToCart(Long detailId, Long productId, int quantity);
    void addToCart(Product product);
    void removeFromCart(Long productId);
    void clearCart();
    Long getTotalAmount(Long detailId);
}
