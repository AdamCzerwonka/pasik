package com.example.pasik.managers;

import com.example.pasik.model.Client;
import com.example.pasik.repositories.ClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientManagerImpl implements ClientManager{
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
        return clientRepository.getById(id);
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
    public void activate(UUID id) throws Exception {
        clientRepository.activate(id);
    }

    @Override
    public void deactivate(UUID id) throws Exception {
        clientRepository.deactivate(id);
    }
}
