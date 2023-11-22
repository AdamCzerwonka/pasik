package com.example.pasik.controllers;

import com.example.pasik.exceptions.LoginAlreadyTakenException;
import com.example.pasik.exceptions.NotFoundException;
import com.example.pasik.managers.ManagerManager;
import com.example.pasik.model.dto.Client.ClientCreateRequest;
import com.example.pasik.model.dto.Manager.ManagerCreateRequest;
import com.example.pasik.model.dto.Manager.ManagerUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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
        var result = managerManager.get();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws NotFoundException {
        var result = managerManager.getById(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/login/many/{login}")
    public ResponseEntity<?> findClientsByLogin(@PathVariable String login) {
        var result = managerManager.findManagersByLogin(login);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/login/single/{login}")
    public ResponseEntity<?> getByLogin(@PathVariable String login) throws NotFoundException {
        var result = managerManager.getByLogin(login);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ManagerCreateRequest request) throws URISyntaxException {
        try {
            var result = managerManager.create(request.ToManager());

            return ResponseEntity.created(new URI("http://localhost:8080/realestate/" + result.getId())).body(result);
        } catch (LoginAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ManagerUpdateRequest request) throws NotFoundException {
        var result = managerManager.update(request.ToManager());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable UUID id) throws NotFoundException {
        managerManager.setActiveStatus(id, true);

        return ResponseEntity.ok("Activated");
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable UUID id) throws NotFoundException {
        managerManager.setActiveStatus(id, false);

        return ResponseEntity.ok("Deactivated");
    }
}
