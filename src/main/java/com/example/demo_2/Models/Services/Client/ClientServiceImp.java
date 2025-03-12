package com.example.demo_2.Models.Services.Client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_2.Models.DAO.Client.IClientDao;
import com.example.demo_2.Models.Entities.Client;

@Service
public class ClientServiceImp implements IClientService{

    @Autowired
    private IClientDao clientDao;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client show(Long id) {
        return clientDao.show(id);
    }

    @Override
    public void store(Client client) {
        clientDao.store(client);
    }

    @Override
    public void update(Long id, Client client) {
        Client existingClient = clientDao.show(id);

        if (existingClient != null) {
            existingClient.setName(client.getName());
            existingClient.setLastname(client.getLastname());
            existingClient.setEmail(client.getEmail());
            clientDao.update(existingClient);
        }
    }

    @Override
    public void delete(Long id) {
        clientDao.delete(id);
    }
}
