package com.example.demo_2.Models.DAO.Detail;

import java.util.List;

import com.example.demo_2.Models.Entities.Detail;
import com.example.demo_2.Models.Entities.Product;

public interface IDetailDao {
    public List<Detail> findAll();
    public Detail show(Long id);
    public void store(Detail detail);
    public void delete(Long id);
    public void update(Detail detail);
    public void addProductToDetail(Long detailId, Product product, int quantity);
}
