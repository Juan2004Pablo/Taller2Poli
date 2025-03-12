package com.example.demo_2.Models.Services.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_2.Models.DAO.Product.IProductDao;
import com.example.demo_2.Models.Entities.Product;

@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public List<Product> index() {
        return productDao.index();
    }

    @Override
    @Transactional(readOnly = true)
    public Product show(Long id) {
        return productDao.show(id);
    }

    @Override
    @Transactional
    public void store(Product product) {
        productDao.store(product);
    }

    @Override
    @Transactional
    public void update(Long id, Product product) {
        Product existingProduct = productDao.show(id);

        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
            productDao.update(existingProduct);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productDao.delete(id);
    }
}
