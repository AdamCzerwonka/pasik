package com.example.pasik.controllers;

import com.example.pasik.managers.UserManager;
import com.example.pasik.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserManager userManager;

    public UserController(final UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public ResponseEntity<List<User>> get() {
        var result = userManager.getAll();

        return ResponseEntity.ok(result);
    }
}
