package com.example.demo_2.Models.DAO.Detail;

import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.DetailProduct;
import com.example.demo_2.Models.Entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class DetailDaoImp implements IDetailDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Detail> findAll() {
        TypedQuery<Detail> query = em.createQuery("SELECT d FROM Detail d", Detail.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Detail> findById(Long id) {
        Detail detail = em.find(Detail.class, id);
        return Optional.ofNullable(detail);
    }

    @Override
    @Transactional
    public Detail save(Detail detail) {
        if (detail.getId() == null) {
            em.persist(detail);
            return detail;
        }
        return em.merge(detail);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(detail -> {
            // Eliminar primero los DetailProduct asociados
            em.createQuery("DELETE FROM DetailProduct dp WHERE dp.detail.id = :detailId")
              .setParameter("detailId", id)
              .executeUpdate();
            
            // Luego eliminar el Detail
            em.remove(detail);
        });
    }

    @Override
    @Transactional
    public Detail update(Detail detail) {
        return em.merge(detail);
    }

    @Override
    @Transactional
    public void addProductToDetail(Long detailId, Product product, int quantity) {
        Detail detail = em.find(Detail.class, detailId);
        if (detail == null) {
            throw new IllegalArgumentException("Detail not found with id: " + detailId);
        }

        Product managedProduct = em.find(Product.class, product.getId());
        if (managedProduct == null) {
            throw new IllegalArgumentException("Product not found with id: " + product.getId());
        }

        // Buscar si ya existe la relación
        TypedQuery<DetailProduct> query = em.createQuery(
            "SELECT dp FROM DetailProduct dp WHERE dp.detail.id = :detailId AND dp.product.id = :productId", 
            DetailProduct.class);
        query.setParameter("detailId", detailId);
        query.setParameter("productId", managedProduct.getId());
        
        List<DetailProduct> results = query.getResultList();
        
        if (!results.isEmpty()) {
            // Actualizar cantidad si ya existe
            DetailProduct existing = results.get(0);
            existing.setQuantity(existing.getQuantity() + quantity);
            em.merge(existing);
        } else {
            // Crear nueva relación
            DetailProduct newDetailProduct = new DetailProduct(detail, managedProduct, quantity);
            em.persist(newDetailProduct);
        }
    }
}