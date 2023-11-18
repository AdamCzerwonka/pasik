package com.example.pasik.repositories.mongo;

import com.example.pasik.model.Client;
import com.example.pasik.model.dto.Client.MgdClient;
import com.example.pasik.repositories.ClientRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Repository
public class MongoClientRepository extends AbstractMongoRepository<MgdClient> implements ClientRepository {
    public MongoClientRepository(MongoClient client, MongoDatabase database) {
        super(client, database, database.getCollection("users", MgdClient.class));
    }

    @Override
    public List<Client> get() throws Exception {
        Bson filter = Filters.eq("_clazz", "client");
        List<Client> result = getCollection()
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

        List<Client> result = getCollection()
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
    public Client getById(UUID id) throws Exception {
        Bson filters = Filters.and(
            Filters.eq("_clazz", "client"),
            Filters.eq("_id", id)
        );
        MgdClient result = getCollection().find(filters).first();
        if (result == null) {
            throw new Exception("Client not found");
        }
        return result.toClient();
    }

    @Override
    public Client getByLogin(String login) throws Exception {
        Bson filters = Filters.and(
                Filters.eq("_clazz", "client"),
                Filters.eq("login", login)
        );
        MgdClient result = getCollection().find(filters).first();
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
        } catch (Exception ignored) {}

        if (found) {
            //TODO change exception
            throw new Exception("Login already in use");
        }

        client.setId(UUID.randomUUID());
        getCollection().insertOne(MgdClient.toMgdClient(client));

        return client;
    }

    @Override
    public Client update(Client client) throws Exception {
        Client existing = getById(client.getId());

        Bson filters = Filters.and(
                Filters.eq("_clazz", "client"),
                Filters.eq("_id", client.getId())
        );
        Bson updates = Updates.combine(
                Updates.set("firstName", client.getFirstName()),
                Updates.set("lastName", client.getFirstName())
        );
        getCollection().updateOne(filters, updates);
        return client;
    }

    @Override
    public void activate(UUID id) throws Exception {
        Client client = getById(id);

        if (client.getActive()) {
            //TODO change exception
            throw new Exception("Client is already activated");
        }

        Bson filters = Filters.and(
                Filters.eq("_clazz", "client"),
                Filters.eq("_id", id)
        );
        Bson update = Updates.set("active", true);
        getCollection().updateOne(filters, update);
    }

    @Override
    public void deactivate(UUID id) throws Exception {
        Client client = getById(id);

        if (!client.getActive()) {
            //TODO change exception
            throw new Exception("Client is already deactivated");
        }
        Bson filters = Filters.and(
                Filters.eq("_clazz", "client"),
                Filters.eq("_id", id)
        );
        Bson update = Updates.set("active", false);
        getCollection().updateOne(filters, update);

    }
}
