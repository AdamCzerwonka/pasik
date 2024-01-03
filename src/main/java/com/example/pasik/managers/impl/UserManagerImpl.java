package com.example.pasik.managers.impl;

import com.example.pasik.managers.UserManager;
import com.example.pasik.model.User;
import com.example.pasik.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagerImpl implements UserManager {
    private final UserRepository userRepository;

    public UserManagerImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}
