package com.example.pasik.managers;

import com.example.pasik.model.User;

import java.util.List;
import java.util.UUID;

public interface UserManager {
    List<User> getAll(String filter);

    User getById(UUID id);

    User getByLogin(String login);
}
