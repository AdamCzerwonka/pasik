package com.example.pasik.repositories;

import com.example.pasik.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();
}
