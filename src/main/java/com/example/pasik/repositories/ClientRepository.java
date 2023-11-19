package com.example.pasik.repositories;

import com.example.pasik.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    List<Client> get() throws Exception;
    List<Client> findClientsByLogin(String login) throws Exception;
    Optional<Client> getById(UUID id);
    Client getByLogin(String login) throws Exception;
    Client create(Client client) throws Exception;
    Client update(Client client) throws Exception;
}
