package com.example.demo_2.Models.DAO.Product;

import com.example.demo_2.Models.Entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDaoImp implements ICategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Category category) {
        em.persist(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findById(Integer id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        TypedQuery<Category> query = em.createQuery(
            "SELECT c FROM Category c", Category.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Category category) {
        em.merge(category);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        findById(id).ifPresent(category -> em.remove(category));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        Long count = em.createQuery(
            "SELECT COUNT(c) FROM Category c WHERE c.name = :name", Long.class)
            .setParameter("name", name)
            .getSingleResult();
        return count > 0;
    }
}