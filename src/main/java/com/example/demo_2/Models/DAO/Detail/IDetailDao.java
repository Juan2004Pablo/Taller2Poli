package com.example.demo_2.Models.DAO.Detail;

import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.Product;
import java.util.List;
import java.util.Optional;

public interface IDetailDao {
    
    // Obtener todos los detalles
    List<Detail> findAll();
    
    // Buscar detalle por ID (debería devolver Optional)
    Optional<Detail> findById(Long id);
    
    // Guardar un nuevo detalle (debería devolver el detalle guardado)
    Detail save(Detail detail);
    
    // Eliminar detalle por ID
    void deleteById(Long id);
    
    // Actualizar detalle existente
    Detail update(Detail detail);
    
    // Añadir producto a un detalle (versión mejorada)
    void addProductToDetail(Long detailId, Product product, int quantity);
    
    // Versión alternativa con Optional
    default void addProductToDetail(Long detailId, Optional<Product> productOptional, int quantity) {
        productOptional.ifPresent(product -> addProductToDetail(detailId, product, quantity));
    }
}