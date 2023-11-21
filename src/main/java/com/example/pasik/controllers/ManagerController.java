package com.example.pasik.controllers;

import com.example.pasik.managers.ManagerManager;
import com.example.pasik.model.dto.Client.ClientCreateRequest;
import com.example.pasik.model.dto.Manager.ManagerCreateRequest;
import com.example.pasik.model.dto.Manager.ManagerUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController()
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerManager managerManager;

    public ManagerController(final ManagerManager managerManager) {
        this.managerManager = managerManager;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        try {
            var result = managerManager.get();

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            var result = managerManager.getById(id);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/login/many/{login}")
    public ResponseEntity<?> findClientsByLogin(@PathVariable String login) {
        try {
            var result = managerManager.findManagersByLogin(login);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/login/single/{login}")
    public ResponseEntity<?> getByLogin(@PathVariable String login) {
        try {
            var result = managerManager.getByLogin(login);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ManagerCreateRequest request) {
        try {
            var result = managerManager.create(request.ToManager());

            return ResponseEntity.created(new URI("http://localhost:8080/realestate/" + result.getId())).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ManagerUpdateRequest request) {
        try {
            var result = managerManager.update(request.ToManager());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable UUID id) {
        try {
            managerManager.setActiveStatus(id, true);

            return ResponseEntity.ok("Activated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable UUID id) {
        try {
            managerManager.setActiveStatus(id, false);

            return ResponseEntity.ok("Deactivated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
