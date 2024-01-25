package com.example.pasik.managers.impl;

import com.example.pasik.managers.UserManager;
import com.example.pasik.model.User;
import com.example.pasik.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserManagerImpl implements UserManager {
    private final UserRepository userRepository;

    public UserManagerImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll(String filter) {
        return userRepository.getAll(filter);
    }

    @Override
    public User getById(UUID id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.getByLogin(login);
    }
}
