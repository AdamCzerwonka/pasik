package com.example.pasik.repositories.mongo;

import com.example.pasik.model.Rent;
import com.example.pasik.model.dto.Rent.MgdRent;
import com.example.pasik.repositories.RentRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MongoRentRepository implements RentRepository {
    private final MongoCollection<MgdRent> collection;

    public MongoRentRepository(final MongoDatabase database) {
        this.collection = database.getCollection("rents", MgdRent.class);
    }

    @Override
    public List<Rent> get() throws Exception {
        List<Rent> result = collection
                .find()
                .into(new ArrayList<>())
                .stream()
                .map(MgdRent::toRent)
                .toList();

        if (result.isEmpty()) {
            //TODO change exception
            throw new Exception("No rents found");
        }

        return result;
    }

    @Override
    public Rent getById(UUID id) throws Exception {
        Bson filter = Filters.eq("_id", id);
        MgdRent result = collection.find(filter).first();
        if (result == null) {
            //TODO change exception
            throw new Exception("Rent not found");
        }
        return result.toRent();
    }

    @Override
    public Rent create(Rent rent) throws Exception {
        // TODO add rent restrictions
        rent.setId(UUID.randomUUID());
        collection.insertOne(MgdRent.toMgdRent(rent));

        return rent;
    }

    @Override
    public Rent update(Rent rent) {
        // TODO implementation
        return null;
    }

    @Override
    public void delete(UUID id) throws Exception {
        Rent rent = getById(id);

        if (rent.getEndDate() != null) {
            //TODO change exception
            throw new Exception("Cannot delete this rent");
        }

        Bson filter = Filters.eq("_id", id);
        collection.deleteOne(filter);
    }

    @Override
    public void endRent(UUID id) throws Exception {
        Rent rent = getById(id);
        if (rent.getEndDate() != null) {
            //TODO change exception
            throw new Exception("Rent is already ended");
        }
        Bson filter = Filters.eq("_id", id);
        Bson update = Updates.set("endDate", LocalDate.now());
        collection.updateOne(filter, update);
    }
}
