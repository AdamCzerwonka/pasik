package com.example.pasik.controllers;

import com.example.pasik.managers.RentManager;
import com.example.pasik.model.Rent;
import com.example.pasik.model.dto.Rent.RentCreateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rent")
public class RentController {
    private final RentManager rentManager;

    public RentController(final RentManager rentManager) {
        this.rentManager = rentManager;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RentCreateRequest rentRequest) throws Exception {
        try {
            var result = rentManager.create(
                    rentRequest.getClientId(),
                    rentRequest.getRealEstateId(),
                    rentRequest.getStartDate());

            return ResponseEntity.created(new URI("http://localhost:8080/rent/" + result.getId())).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<?> endRent(@PathVariable UUID id) throws Exception {
        try {
            rentManager.endRent(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Rent>> get() {
        return ResponseEntity.ok(rentManager.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            Rent result = rentManager.getById(id);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws Exception {
        try {
            rentManager.delete(id);

            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
