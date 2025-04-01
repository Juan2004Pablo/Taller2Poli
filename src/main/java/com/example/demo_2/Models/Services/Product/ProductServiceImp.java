package com.example.demo_2.Models.Services.Product;

import com.example.demo_2.Models.DAO.Product.IProductDao;
import com.example.demo_2.Models.Entities.Products;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductServiceImp implements IProductService {

    private final IProductDao productDao;

   // @Autowired
    public ProductServiceImp(IProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Products> findAll() {
        return productDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Products findById(Long id) {
        return productDao.findById(id);  // Ahora retorna un Product directamente
    }
    

    @Override
    @Transactional
    public void save(Products product) {
        productDao.save(product);
    }

    @Override
    @Transactional
    public void update(Products product) {
        productDao.update(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Products> findByName(String name) {
        return productDao.findByName(name);
    }
}