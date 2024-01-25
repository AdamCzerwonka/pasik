package com.example.pasik.controllers;

import com.example.pasik.managers.UserManager;
import com.example.pasik.model.User;
import com.example.pasik.model.dto.User.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserManager userManager;

    public UserController(final UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> get(@RequestParam(defaultValue = "") String filter) {
        var result = userManager.getAll(filter).stream().map(UserResponse::fromUser).toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        var result = userManager.getById(id);
        return ResponseEntity.ok(UserResponse.fromUser(result));
    }
}
