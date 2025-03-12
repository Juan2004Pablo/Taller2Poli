package com.example.demo_2.Models.Services.Client;

import java.util.List;

import com.example.demo_2.Models.Entities.Client;

public interface IClientService {
    public List<Client> findAll();
    public Client show(Long id);
    public void store(Client client);
    public void update(Long id, Client client);
    public void delete(Long id);
}
