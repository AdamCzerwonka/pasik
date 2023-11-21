package com.example.pasik.controllers;

import com.example.pasik.managers.AdministratorManager;
import com.example.pasik.model.dto.Administrator.AdministratorCreateRequest;
import com.example.pasik.model.dto.Administrator.AdministratorUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
        try {
            var result = administratorManager.get();

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            var result = administratorManager.getById(id);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/login/many/{login}")
    public ResponseEntity<?> findAdministratorsByLogin(@PathVariable String login) {
        try {
            var result = administratorManager.findAdministratorsByLogin(login);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/login/single/{login}")
    public ResponseEntity<?> getByLogin(@PathVariable String login) {
        try {
            var result = administratorManager.getByLogin(login);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AdministratorCreateRequest request) {
        try {
            var result = administratorManager.create(request.ToAdministrator());

            return ResponseEntity.created(new URI("http://localhost:8080/realestate/" + result.getId())).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody AdministratorUpdateRequest request) {
        try {
            var result = administratorManager.update(request.ToAdministrator());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable UUID id) {
        try {
            administratorManager.setActiveStatus(id, true);

            return ResponseEntity.ok("Activated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable UUID id) {
        try {
            administratorManager.setActiveStatus(id, false);

            return ResponseEntity.ok("Deactivated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
