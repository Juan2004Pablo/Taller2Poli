package com.example.demo_2.Models.DAO.Product;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_2.Models.Entities.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProductDaoImp implements IProductDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Product> index() {
        return em.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Product show(Long id) {
        return em.find(Product.class, id);
    }

    @Override
    @Transactional
    public void store(Product product) {
        em.persist(product);
    }

    @Override
    @Transactional
    public void update(Product product) {
        em.merge(product);
    }

    @Override
    public void delete(Long id) {
        Product product = show(id);

        if (product != null) {
            em.remove(product);
        }
    }
}
