package com.example.demo_2.Services.impl;

import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.DetailsProduct;
import com.example.demo_2.Models.Entities.Product;
import com.example.demo_2.Models.Entities.User;
import com.example.demo_2.Services.DetailService;
import com.example.demo_2.Services.ProductService;
import com.example.demo_2.Repository.DetailRepository;
import com.example.demo_2.Repository.DetailsProductRepository;
import com.example.demo_2.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class CartServiceImpl implements DetailService {

    @Autowired
    private DetailRepository detailRepository;

    @Autowired
    private DetailsProductRepository detailsProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    // Manejamos un carrito en sesión
    private List<Detail> cartDetails = new ArrayList<>();

    public List<Detail> getCartDetails() {
        return cartDetails;
    }

    @Transactional
    public Detail getCartDetailById(Long detailId) {
        Optional<Detail> optionalDetail = detailRepository.findById(detailId);
        if(optionalDetail.isPresent()){
            return optionalDetail.get();
        } else {
            throw new RuntimeException("No se encontró el detalle con ID 1");
        }
    }

    @Transactional
    public void addProductToCart(Long detailId, Long productId, int quantity) {
        Optional<Detail> optionalDetail = detailRepository.findById(detailId);
        Optional<Product> optionalProduct = Optional.ofNullable(productService.findById(productId));
    
        if (optionalDetail.isPresent() && optionalProduct.isPresent()) {
            Detail detail = optionalDetail.get();
            Product product = optionalProduct.get();
            boolean found = false;
    
            if (detail.getDetailsProducts() != null) {
                for (DetailsProduct dp : detail.getDetailsProducts()) {
                    if (dp.getProduct().getIdProduct().equals(productId)) {
                        dp.setQuantity(dp.getQuantity() + quantity);
                        detailsProductRepository.save(dp);
                        found = true;
                        break;
                    }
                }
            }
    
            if (!found) {
                DetailsProduct dp = new DetailsProduct();
                dp.setDetail(detail); // 🔹 Asignar correctamente el detalle
                dp.setProduct(product); // 🔹 Asignar correctamente el producto
                dp.setQuantity(quantity);
    
                detailsProductRepository.save(dp);
            }
    
            detailRepository.save(detail);
        }
    }    

    
    @Transactional
    public void addToCart(Product product) {
        // Este método se ajusta para el manejo en sesión, si se desea persistir, se puede utilizar el repositorio
        for (Detail detail : cartDetails) {
            if (detail.getDetailsProducts() != null) {
                for (DetailsProduct dp : detail.getDetailsProducts()) {
                    if (dp.getProduct().getIdProduct().equals(product.getIdProduct())) {
                        dp.setQuantity(dp.getQuantity() + 1);
                        return;
                    }
                }
            }
        }
        Detail newDetail = new Detail();
        newDetail.setCreateAt(LocalDateTime.now());
        // Inicializamos la lista de productos
        newDetail.setDetailsProducts(new ArrayList<>());
        DetailsProduct newDp = new DetailsProduct();
        newDp.setDetail(newDetail);
        newDp.setProduct(product);
        newDp.setQuantity(1);
        newDetail.getDetailsProducts().add(newDp);
        cartDetails.add(newDetail);
    }

    @Transactional
    public void removeProductFromDetail(Long productId, Long detailId) {
        DetailsProduct detailsProduct = detailsProductRepository.findByProduct_IdProductAndDetail_IdDetail(productId, detailId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en el detalle"));
    
        // Eliminar de la lista de la entidad padre
        detailsProduct.getDetail().getDetailsProducts().remove(detailsProduct);
    
        // Eliminar de la base de datos
        detailsProductRepository.delete(detailsProduct);
    }

    public void clearCart() {
        cartDetails.clear();
    }

    @Override
    @Transactional(readOnly = true)
    public Detail show(Long id) {
        return detailRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void store(Detail detail) {
        detail.setCreateAt(LocalDateTime.now());
        detail.setStatus("ACTIVE");
        
        // Crear un usuario dummy
        User dummyUser = new User();
        dummyUser.setIdentification(1L);
        dummyUser.setName("Dummy User");
        dummyUser.setAddress("Dummy Address");
        dummyUser.setPhone("0000000000");
        dummyUser.setEmail("dummy@example.com");
        dummyUser.setPassword("dummy");
        
        // Verificar si ya existe un usuario con identification = 1 en la base de datos
        if (!userRepository.existsById(dummyUser.getIdentification())) {
            userRepository.save(dummyUser);
        }
        
        // Asignar el usuario dummy al detalle
        detail.setUser(dummyUser);
        detail.setIdentification(dummyUser.getIdentification());
        
        detailRepository.save(detail);
    }    

    @Override
    @Transactional
    public void update(Detail detail) {
        detailRepository.save(detail);
    }

    public Long getTotalAmount(Long detailId) {
        Detail detail = this.show(detailId);
        if (detail == null || detail.getDetailsProducts() == null || detail.getDetailsProducts().isEmpty()) {
            return 0L;
        }
        return detail.getDetailsProducts().stream()
                .mapToLong(dp -> dp.getProduct().getPrice() * dp.getQuantity())
                .sum();
    }

    @Transactional(readOnly = true)
    public Long getLatestActiveDetailId() {
        return detailRepository.findLatestActiveDetail()
                .map(Detail::getIdDetail)
                .orElse(null); // Devuelve null si no hay detalles activos
    }

    @Transactional
    public Long countProductsInDetail(Long detailId) {
        return detailsProductRepository.countProductsInDetail(detailId);
    }

    @Override
    public void removeFromCart(Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFromCart'");
    }
}
