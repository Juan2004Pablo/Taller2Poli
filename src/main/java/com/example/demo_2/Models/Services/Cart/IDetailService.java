package com.example.demo_2.Models.Services.Cart;

import java.util.List;

import com.example.demo_2.Models.Entities.Detail;

public interface IDetailService {
    public void store(Detail detail);
    public Detail show(Long id);
    public void update(Detail detail);
    /*public List<Detail> findAll();
    public void store(Detail detail);
    public void update(Long id, Detail detail);
    public void delete(Long id);*/
}
