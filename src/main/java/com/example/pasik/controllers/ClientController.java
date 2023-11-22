package com.example.pasik.controllers;

import com.example.pasik.exceptions.LoginAlreadyTakenException;
import com.example.pasik.exceptions.NotFoundException;
import com.example.pasik.managers.ClientManager;
import com.example.pasik.managers.RentManager;
import com.example.pasik.model.Rent;
import com.example.pasik.model.dto.Client.ClientCreateRequest;
import com.example.pasik.model.dto.Client.ClientUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/client")
public class ClientController {
    private final ClientManager clientManager;
    private final RentManager rentManager;

    public ClientController(final ClientManager clientManager, final RentManager rentManager) {
        this.clientManager = clientManager;
        this.rentManager = rentManager;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        var result = clientManager.get();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws NotFoundException {
        var result = clientManager.getById(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/rents")
    public ResponseEntity<List<Rent>> getRents(@PathVariable UUID id, @RequestParam(defaultValue = "true") boolean current) {
        return ResponseEntity.ok(rentManager.getByClientId(id, current));
    }

    @GetMapping("/login/many/{login}")
    public ResponseEntity<?> findClientsByLogin(@PathVariable String login) {
        var result = clientManager.findClientsByLogin(login);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/login/single/{login}")
    public ResponseEntity<?> getByLogin(@PathVariable String login) throws NotFoundException {
        var result = clientManager.getByLogin(login);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClientCreateRequest request) throws URISyntaxException {
        try {
            var result = clientManager.create(request.ToClient());

            return ResponseEntity.created(new URI("http://localhost:8080/realestate/" + result.getId())).body(result);
        } catch (LoginAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ClientUpdateRequest request) throws NotFoundException {
        var result = clientManager.update(request.ToClient());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable UUID id) throws NotFoundException {
        clientManager.setActiveStatus(id, true);

        return ResponseEntity.ok("Activated");
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable UUID id) throws NotFoundException {
        clientManager.setActiveStatus(id, false);

        return ResponseEntity.ok("Deactivated");

    }
}
