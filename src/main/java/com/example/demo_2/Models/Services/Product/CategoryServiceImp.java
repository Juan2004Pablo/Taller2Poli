package com.example.demo_2.Models.Services.Product;

import com.example.demo_2.Models.DAO.Product.ICategoryDao;
import com.example.demo_2.Models.Entities.Category;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements ICategoryService {

    private final ICategoryDao categoryDao;

    //@Autowired
    public CategoryServiceImp(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findById(Integer id) {
        return categoryDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Category category) {
        if (categoryDao.existsByName(category.getName())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        categoryDao.save(category);
    }

    @Override
    @Transactional
    public void update(Category category) {
        categoryDao.update(category);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        categoryDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryDao.existsByName(name);
    }
}