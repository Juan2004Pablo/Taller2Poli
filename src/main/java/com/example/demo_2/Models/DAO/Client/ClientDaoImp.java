package com.example.demo_2.Models.DAO.Client;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_2.Models.Entities.Client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ClientDaoImp implements IClientDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return em.createQuery("from Client", Client.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Client show(Long id) {
        return em.find(Client.class, id);
    }

    @Override
    @Transactional
    public void store(Client client) {
        em.persist(client);
    }
    
    @Override
    @Transactional
    public void update(Client client) {
        em.merge(client);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Client client = show(id);

        if (client != null) {
            em.remove(client);
        }
    }
}
