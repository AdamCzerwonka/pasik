package com.example.pasik.repositories.mongo;

import com.example.pasik.model.RealEstate;
import com.example.pasik.model.dto.RealEstate.MgdRealEstate;
import com.example.pasik.repositories.RealEstateRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        MongoCollection<MgdRealEstate> collection = mongoClient.getDatabase("pas").getCollection("realEstates", MgdRealEstate.class);
        return collection.find().into(new ArrayList<>()).stream().map(MgdRealEstate::toRealEstate).toList();
    }

    @Override
    public Optional<RealEstate> getById(UUID id) {
        MongoCollection<MgdRealEstate> collection = mongoClient.getDatabase("pas").getCollection("realEstates", MgdRealEstate.class);
        Bson filter = Filters.eq("_id", id);
        MgdRealEstate result = collection.find(filter).first();
        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(result.toRealEstate());
    }

    @Override
    public RealEstate create(RealEstate realEstate) {
        MongoCollection<MgdRealEstate> collection = mongoClient.getDatabase("pas").getCollection("realEstates", MgdRealEstate.class);
        realEstate.setId(UUID.randomUUID());
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
