package com.example.demo_2.Models.DAO.Product;

import com.example.demo_2.Models.Entities.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class ProductDaoImp implements IProductDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Products> findAll() {
        // Usar nombres de columnas exactos como están en la BD
        return em.createQuery(
            "SELECT p FROM Product p LEFT JOIN FETCH p.category", 
            Products.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Products findById(Long id) {
        return em.find(Products.class, id);  // Devuelve un producto o null si no se encuentra
    }
    @Override
    @Transactional
    public void save(Products product) {
        if (product.getIdProduct() == null) {
            em.persist(product);  // Si el producto no tiene ID, lo persistimos
        } else {
            em.merge(product);  // Si el producto tiene ID, lo actualizamos
        }
    }

    @Override
    @Transactional
    public void update(Products product) {
        if (product != null) {
            em.merge(product);  // Actualizamos el producto en la BD
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Products product = findById(id);
        if (product != null) {
            em.remove(product);  // Eliminamos el producto de la BD
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Products> findByName(String name) {
        // Usamos una consulta JPQL para buscar productos por nombre (usando LIKE)
        return em.createQuery("SELECT p FROM Product p WHERE p.name LIKE :name", Products.class)
                 .setParameter("name", "%" + name + "%")  // El operador LIKE busca coincidencias parciales
                 .getResultList();
    }
}
