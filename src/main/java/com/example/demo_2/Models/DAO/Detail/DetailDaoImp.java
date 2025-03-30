package com.example.demo_2.Models.DAO.Detail;

import java.util.List;

import com.example.demo_2.Models.Entities.Client;
import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.DetailProduct;
import com.example.demo_2.Models.Entities.Product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class DetailDaoImp implements IDetailDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Detail> findAll() {
        return em.createQuery("from Detail", Detail.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Detail show(Long id) {
        return em.find(Detail.class, id);
    }

    @Override
    @Transactional
    public void store(Detail detail) {
        em.persist(detail);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Detail detail = show(id);

        if (detail != null) {
            em.remove(detail);
        }
    }

    @Override
    @Transactional
    public void addProductToDetail(Long detailId, Product product, int quantity) {
        Detail detail = em.find(Detail.class, detailId);

        if (detail != null) {
            // Buscar si el producto ya está en el detalle
            for (DetailProduct dp : detail.getDetailProducts()) {
                if (dp.getProduct().getId().equals(product.getId())) {
                    // Si ya existe, incrementamos la cantidad
                    dp.setQuantity(dp.getQuantity() + quantity);
                    em.merge(dp);
                    return;
                }
            }

            // Si el producto no está en el detalle, lo añadimos
            DetailProduct newDetailProduct = new DetailProduct(detail, product, quantity);
            detail.getDetailProducts().add(newDetailProduct);

            em.persist(newDetailProduct); // Guardamos en la tabla pivote
            em.merge(detail); // Actualizamos el detalle
        }
    }

    @Override
    @Transactional
    public void update(Detail detail) {
        em.merge(detail);
    }
}
