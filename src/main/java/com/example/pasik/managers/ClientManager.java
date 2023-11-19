package com.example.pasik.managers;

import com.example.pasik.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientManager {
    List<Client> get() throws Exception;
    List<Client> findClientsByLogin(String login) throws Exception;
    Client getById(UUID id) throws Exception;
    Client getByLogin(String login) throws Exception;
    Client create(Client client) throws Exception;
    Client update(Client client) throws Exception;
    void setActiveStatus(UUID id, boolean active) throws Exception;
}
