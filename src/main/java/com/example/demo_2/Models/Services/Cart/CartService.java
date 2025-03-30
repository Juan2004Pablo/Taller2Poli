package com.example.demo_2.Models.Services.Cart;

import com.example.demo_2.Models.DAO.Client.IClientDao;
import com.example.demo_2.Models.DAO.Detail.IDetailDao;
import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.DetailProduct;
import com.example.demo_2.Models.Entities.Product;
import com.example.demo_2.Models.Services.Product.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService implements IDetailService {

    @Autowired
    private IDetailDao detailDao;

    @Autowired
    private IProductService productService;

    // Usamos una lista de Detail en lugar de CartItem
    private List<Detail> cartDetails = new ArrayList<>();

    public List<Detail> getCartDetails() {
        return cartDetails;
    }

    public Detail getCartDetailById() {
        Detail detail = detailDao.show(1L);

        if (detail == null) {
            throw new RuntimeException("No se encontró el detalle con ID 1");
        }

        return detail;
    }

    public void addProductToCart(Long detailId, Long productId, int quantity) {
        Product product = productService.show(productId);
        if (product != null) {
            detailDao.addProductToDetail(detailId, product, quantity);
        }
    }

    public void addToCart(Product product) {
        for (Detail detail : cartDetails) {
            for (DetailProduct detailProduct : detail.getDetailProducts()) {
                if (detailProduct.getProduct().getId().equals(product.getId())) {
                    // Si el producto ya está en el carrito, incrementar la cantidad
                    detailProduct.setQuantity(detailProduct.getQuantity() + 1);
                    return;
                }
            }
        }

        // Si no existe, se crea un nuevo Detail con el producto y cantidad 1
        Detail newDetail = new Detail();
        DetailProduct newDetailProduct = new DetailProduct(newDetail, product, 1);

        newDetail.getDetailProducts().add(newDetailProduct);
        cartDetails.add(newDetail);
    }

    public void removeFromCart(Long productId) {
        cartDetails.removeIf(detail -> {
            // Buscar si el producto está en los detalles de la orden
            for (DetailProduct detailProduct : detail.getDetailProducts()) {
                if (detailProduct.getProduct().getId().equals(productId)) {
                    // Si la cantidad es mayor a 1, restar 1, si no, eliminar
                    if (detailProduct.getQuantity() > 1) {
                        detailProduct.setQuantity(detailProduct.getQuantity() - 1);
                        return false;
                    }
                    return true;
                }
            }
            return false;
        });
    }    

    public void clearCart() {
        cartDetails.clear();
    }

    @Override
    @Transactional(readOnly = true)
    public Detail show(Long id) {
        return detailDao.show(id);
    }

    @Override
    @Transactional
    public void store(Detail detail) {
        detailDao.store(detail);
    }

    @Override
    @Transactional
    public void update(Detail detail) {
        detailDao.update(detail);
    }

    public Long getTotalAmount(Long detailId) {
        Detail detail = this.show(detailId);

        if (detail == null || detail.getDetailProducts().isEmpty()) {
            return 0L; // Retorna 0 si el carrito está vacío
        }

        return detail.getDetailProducts().stream()
                .mapToLong(dp -> dp.getProduct().getPrice() * dp.getQuantity())
                .sum();
    }

    // Método auxiliar para obtener la cantidad de un producto en el detail
    private int getQuantityForProduct(Detail detail, Product product) {
        // Asumiendo que la relación se maneja en una tabla pivote
        return detail.getDetailProducts()
                .stream()
                .filter(dp -> dp.getProduct().getId().equals(product.getId()))
                .mapToInt(DetailProduct::getQuantity)
                .findFirst()
                .orElse(0);
    }
}
