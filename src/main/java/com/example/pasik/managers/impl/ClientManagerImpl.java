package com.example.pasik.managers.impl;

import com.example.pasik.managers.ClientManager;
import com.example.pasik.model.Client;
import com.example.pasik.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientManagerImpl implements ClientManager {
    private final ClientRepository clientRepository;

    public ClientManagerImpl(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> get() throws Exception {
        return clientRepository.get();
    }

    @Override
    public List<Client> findClientsByLogin(String login) throws Exception {
        return clientRepository.findClientsByLogin(login);
    }

    @Override
    public Client getById(UUID id) throws Exception {
        Optional<Client> client = clientRepository.getById(id);
        if (client.isEmpty()) {
            throw new Exception("Client not found");
        }

        return client.get();
    }

    @Override
    public Client getByLogin(String login) throws Exception {
        return clientRepository.getByLogin(login);
    }

    @Override
    public Client create(Client client) throws Exception {
        return clientRepository.create(client);
    }

    @Override
    public Client update(Client client) throws Exception {
        return clientRepository.update(client);
    }

    @Override
    public void setActiveStatus(UUID id, boolean active) throws Exception {
        Optional<Client> clientResult = clientRepository.getById(id);
        if (clientResult.isEmpty()) {
            throw new RuntimeException("Not found");
        }

        Client client = clientResult.get();
        client.setActive(active);

        clientRepository.update(client);
    }
}
