package com.example.pasik.controllers;

import com.example.pasik.managers.RentManager;
import com.example.pasik.model.RealEstate;
import com.example.pasik.model.Rent;
import com.example.pasik.model.dto.Rent.RentRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<Rent> create(@RequestBody @Valid RentRequest rentRequest) throws Exception {
        var result = rentManager.create(
                rentRequest.getClientId(),
                rentRequest.getRealEstateId(),
                rentRequest.getStartDate());
        return ResponseEntity.created(new URI("http://localhost:8080/rent/" + result.getId())).body(result);
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<?> endRent(@PathVariable UUID id) throws Exception {
        rentManager.endRent(id);
        return ResponseEntity.ok().build();
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

    @GetMapping("{/id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws Exception {
        rentManager.delete(id);

        return ResponseEntity.notFound().build();
    }
}
