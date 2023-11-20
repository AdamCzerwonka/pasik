package com.example.pasik.controllers;

import com.example.pasik.managers.ClientManager;
import com.example.pasik.managers.RentManager;
import com.example.pasik.model.Rent;
import com.example.pasik.model.dto.Client.ClientCreateRequest;
import com.example.pasik.model.dto.Client.ClientUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
        try {
            var result = clientManager.get();

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            var result = clientManager.getById(id);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/rents")
    public ResponseEntity<List<Rent>> getRents(@PathVariable UUID id, @RequestParam(defaultValue = "true") boolean current) {
        return ResponseEntity.ok(rentManager.getByClientId(id, current));
    }

    @GetMapping("/login/many/{login}")
    public ResponseEntity<?> findClientsByLogin(@PathVariable String login) {
        try {
            var result = clientManager.findClientsByLogin(login);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/login/single/{login}")
    public ResponseEntity<?> getByLogin(@PathVariable String login) {
        try {
            var result = clientManager.getByLogin(login);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClientCreateRequest request) {
        try {
            var result = clientManager.create(request.ToClient());

            return ResponseEntity.created(new URI("http://localhost:8080/realestate/" + result.getId())).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ClientUpdateRequest request) {
        try {
            var result = clientManager.update(request.ToClient());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestBody UUID id) {
        try {
            clientManager.setActiveStatus(id, true);

            return ResponseEntity.ok("Activated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deactivate")
    public ResponseEntity<?> deactivate(@RequestBody UUID id) {
        try {
            clientManager.setActiveStatus(id, false);

            return ResponseEntity.ok("Deactivated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
