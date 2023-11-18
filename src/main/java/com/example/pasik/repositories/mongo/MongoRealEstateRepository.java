package com.example.pasik.repositories.mongo;

import com.example.pasik.model.RealEstate;
import com.example.pasik.model.mongo.MgdRealEstate;
import com.example.pasik.repositories.RealEstateRepository;
import com.mongodb.client.MongoClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MongoRealEstateRepository implements RealEstateRepository {
    private final MongoClient mongoClient;

    public MongoRealEstateRepository(final MongoClient client) {
        this.mongoClient = client;
    }

    @Override
    public List<RealEstate> get() {
        return null;
    }

    @Override
    public Optional<RealEstate> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public RealEstate create(RealEstate realEstate) {
        var collection = mongoClient.getDatabase("pas").getCollection("realEstates", MgdRealEstate.class);
        collection.insertOne(MgdRealEstate.toMgdRealEstate(realEstate));

        return realEstate;
    }

    @Override
    public RealEstate update(RealEstate realEstate) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
