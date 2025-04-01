package com.example.demo_2.Models.DAO.Detail;

import com.example.demo_2.Models.Entities.Details;
import com.example.demo_2.Models.Entities.DetailsProducts;
import com.example.demo_2.Models.Entities.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DetailDaoImp implements IDetailDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Details> findAll() {
        return em.createQuery("SELECT d FROM Details d", Details.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Details findById(Long id) {
        return em.find(Details.class, id);
    }

    @Override
    public Details save(Details detail) {
        if (detail.getIdDetail() == null) {
            em.persist(detail);
            return detail;
        }
        return em.merge(detail);
    }

    @Override
    public void deleteById(Long id) {
        Details detail = findById(id);
        if (detail != null) {
            // Eliminar primero los DetailsProduct asociados
            em.createQuery("DELETE FROM DetailsProducts dp WHERE dp.details.idDetail = :detailId")
              .setParameter("detailId", id)
              .executeUpdate();
            
            // Luego eliminar el Detail
            em.remove(detail);
        }
    }

    @Override
    public Details update(Details detail) {
        return em.merge(detail);
    }

    @Override
    public void addProductToDetail(Long detailId, Products product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }

        Details detail = em.find(Details.class, detailId);
        if (detail == null) {
            throw new IllegalArgumentException("Detalle no encontrado con id: " + detailId);
        }

        Products managedProduct = em.find(Products.class, product.getIdProduct());
        if (managedProduct == null) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        if (managedProduct.getStock() < quantity) {
            throw new IllegalStateException("Stock insuficiente");
        }

        // Buscar relación existente
        TypedQuery<DetailsProducts> query = em.createQuery(
            "SELECT dp FROM DetailsProducts dp WHERE dp.details.idDetail = :detailId AND dp.products.idProduct = :productId", 
            DetailsProducts.class);
        query.setParameter("detailId", detailId);
        query.setParameter("productId", managedProduct.getIdProduct());
        
        List<DetailsProducts> results = query.getResultList();
        
        if (!results.isEmpty()) {
            // Actualizar cantidad existente
            DetailsProducts existing = results.get(0);
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            // Crear nueva relación
            DetailsProducts newDetailProduct = new DetailsProducts();
            newDetailProduct.setDetails(detail);
            newDetailProduct.setProduct(managedProduct);
            newDetailProduct.setQuantity(quantity);
            em.persist(newDetailProduct);
        }

        // Actualizar stock
        managedProduct.setStock(managedProduct.getStock() - quantity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetailsProducts> findProductsByDetailId(Long detailId) {
        return em.createQuery(
            "SELECT dp FROM DetailsProducts dp WHERE dp.details.idDetail = :detailId", 
            DetailsProducts.class)
            .setParameter("detailId", detailId)
            .getResultList();
    }
}
