package com.example.pasik.repositories.mongo;

import com.example.pasik.model.User;
import com.example.pasik.model.dto.User.MgdUser;
import com.example.pasik.repositories.UserRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MongoUserRepository implements UserRepository {
    private final MongoCollection<MgdUser> collection;

    public MongoUserRepository(MongoDatabase database) {
        this.collection = database.getCollection("users", MgdUser.class);
    }

    @Override
    public List<User> getAll() {
        return collection.find().into(new ArrayList<>()).stream().map(MgdUser::MgdUserToUser).toList();
    }
}
