package com.example.pasik.repositories.mongo;

import com.example.pasik.model.Client;
import com.example.pasik.model.dto.Client.MgdClient;
import com.example.pasik.repositories.ClientRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Repository
public class MongoClientRepository implements ClientRepository {
    private final MongoCollection<MgdClient> collection;

    public MongoClientRepository(MongoDatabase database) {
        this.collection = database.getCollection("users", MgdClient.class);
    }

    @Override
    public List<Client> get() throws Exception {
        Bson filter = Filters.eq("_clazz", "client");
        List<Client> result = collection
                .find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(MgdClient::toClient)
                .toList();

        if (result.isEmpty()) {
            //TODO change exception
            throw new Exception("No clients found");
        }
        return result;
    }

    @Override
    public List<Client> findClientsByLogin(String login) throws Exception {
        Pattern pattern = Pattern.compile(login, Pattern.CASE_INSENSITIVE);
        Bson filters = Filters.and(
                Filters.eq("_clazz", "client"),
                Filters.regex("login", pattern)
        );

        List<Client> result = collection
                .find(filters)
                .into(new ArrayList<>())
                .stream()
                .map(MgdClient::toClient)
                .toList();

        if (result.isEmpty()) {
            //TODO change exception
            throw new Exception("No clients found");
        }
        return result;
    }

    @Override
    public Optional<Client> getById(UUID id) {
        Bson filters = Filters.and(
                Filters.eq("_clazz", "client"),
                Filters.eq("_id", id)
        );
        MgdClient result = collection.find(filters).first();
        if (result == null) {
            return Optional.empty();
        }
        return Optional.of(result.toClient());
    }

    @Override
    public Client getByLogin(String login) throws Exception {
        Bson filters = Filters.and(
                Filters.eq("_clazz", "client"),
                Filters.eq("login", login)
        );
        MgdClient result = collection.find(filters).first();
        if (result == null) {
            throw new Exception("Client not found");
        }
        return result.toClient();
    }

    @Override
    public Client create(Client client) throws Exception {
        boolean found = false;
        try {
            Client existing = getByLogin(client.getLogin());
            found = true;
        } catch (Exception ignored) {
        }

        if (found) {
            //TODO change exception
            throw new Exception("Login already in use");
        }

        client.setId(UUID.randomUUID());
        collection.insertOne(MgdClient.toMgdClient(client));

        return client;
    }

    @Override
    public Client update(Client client) throws Exception {
        Bson filters = Filters.and(
                Filters.eq("_clazz", "client"),
                Filters.eq(MgdClient.ID, client.getId())
        );
        Bson updates = Updates.combine(
                Updates.set(MgdClient.FIRST_NAME, client.getFirstName()),
                Updates.set(MgdClient.LAST_NAME, client.getFirstName()),
                Updates.set(MgdClient.ACTIVE, client.getActive())
        );
        collection.updateOne(filters, updates);
        return client;
    }
}
