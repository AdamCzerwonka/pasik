package com.example.pasik.repositories.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class AbstractMongoRepository<T> implements AutoCloseable {
    public AbstractMongoRepository(MongoClient client, MongoDatabase database, MongoCollection<T> collection) {
        mongoClient = client;
        db = database;
        this.collection = collection;
    }

    private final MongoClient mongoClient;
    private final MongoDatabase db;
    private final MongoCollection<T> collection;

    protected MongoDatabase getDb() {
        return db;
    }

    protected MongoCollection<T> getCollection() {
        return collection;
    }

    protected MongoClient getMongoClient() {
        return mongoClient;
    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }
}