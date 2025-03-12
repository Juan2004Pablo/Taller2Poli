package com.example.demo_2.Models.DAO.Client;

import java.util.List;

import com.example.demo_2.Models.Entities.Client;

public interface IClientDao {
    public List<Client> findAll();
    public Client show(Long id);
    public void store(Client client);
    public void update(Client client);
    public void delete(Long id);
}