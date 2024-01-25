package com.example.pasik.controllers;

import com.example.pasik.exceptions.LoginAlreadyTakenException;
import com.example.pasik.exceptions.NotFoundException;
import com.example.pasik.managers.AdministratorManager;
import com.example.pasik.model.dto.Administrator.AdministratorCreateRequest;
import com.example.pasik.model.dto.Administrator.AdministratorUpdateRequest;
import com.example.pasik.model.dto.User.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController()
@RequestMapping("/administrator")
public class AdministratorController {
    private final AdministratorManager administratorManager;

    public AdministratorController(final AdministratorManager administratorManager) {
        this.administratorManager = administratorManager;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        var result = administratorManager.get().stream().map(UserResponse::fromUser).toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws NotFoundException {
        var result = administratorManager.getById(id);

        return ResponseEntity.ok(UserResponse.fromUser(result));
    }

    @GetMapping("/login/many/{login}")
    public ResponseEntity<?> findAdministratorsByLogin(@PathVariable String login) {
        var result = administratorManager.findAdministratorsByLogin(login).stream().map(UserResponse::fromUser).toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/login/single/{login}")
    public ResponseEntity<?> getByLogin(@PathVariable String login) throws NotFoundException {
        var result = administratorManager.getByLogin(login);

        return ResponseEntity.ok(UserResponse.fromUser(result));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AdministratorCreateRequest request) throws URISyntaxException {
        try {
            var result = administratorManager.create(request.ToAdministrator());

            return ResponseEntity.created(new URI("http://localhost:8080/realestate/" + result.getId())).body(UserResponse.fromUser(result));
        } catch (LoginAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody AdministratorUpdateRequest request) throws NotFoundException {
        var result = administratorManager.update(request.ToAdministrator());

        return ResponseEntity.ok(UserResponse.fromUser(result));
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable UUID id) throws NotFoundException {
        administratorManager.setActiveStatus(id, true);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable UUID id) throws NotFoundException {
        administratorManager.setActiveStatus(id, false);

        return ResponseEntity.ok().build();

    }
}
