package com.example.pasik.repositories.mongo;

import com.example.pasik.model.Rent;
import com.example.pasik.model.dto.Rent.MgdRent;
import com.example.pasik.repositories.RentRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MongoRentRepository extends AbstractMongoRepository<MgdRent> implements RentRepository {

    public MongoRentRepository(MongoClient client, MongoDatabase database) {
        super(client, database, database.getCollection("rents", MgdRent.class));
    }

    @Override
    public List<Rent> get() {
        var result = getCollection().find().into(new ArrayList<>());
        return result.stream().map(MgdRent::toRent).toList();
    }

    @Override
    public Rent getById(UUID id) {
        Bson filter = Filters.eq("_id", id);
        MgdRent result = getCollection().find(filter).first();
        if (result == null) {
            return null;
        }
        return result.toRent();
    }

    @Override
    public Rent create(Rent rent) {
        rent.setId(UUID.randomUUID());
        getCollection().insertOne(MgdRent.toMgdRent(rent));

        return rent;
    }

    @Override
    public Rent update(Rent rent) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
