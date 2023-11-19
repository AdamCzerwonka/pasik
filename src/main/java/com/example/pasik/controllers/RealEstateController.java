package com.example.pasik.controllers;

import com.example.pasik.managers.RealEstateManager;
import com.example.pasik.managers.RentManager;
import com.example.pasik.model.RealEstate;
import com.example.pasik.model.Rent;
import com.example.pasik.model.dto.RealEstate.RealEstateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController()
@RequestMapping("/realestate")
public class RealEstateController {
    private final RealEstateManager realEstateManager;
    private final RentManager rentManager;

    public RealEstateController(final RealEstateManager realEstateManager, final RentManager rentManager) {
        this.realEstateManager = realEstateManager;
        this.rentManager = rentManager;
    }

    @PostMapping
    public ResponseEntity<RealEstate> create(@Valid @RequestBody RealEstateRequest request) throws URISyntaxException {
        var result = realEstateManager.create(request.toRealEstate());
        return ResponseEntity.created(new URI("http://localhost:8080/realestate/" + result.getId())).body(result);
    }

    @GetMapping
    public ResponseEntity<List<RealEstate>> get() {
        return ResponseEntity.ok(realEstateManager.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            RealEstate result = realEstateManager.getById(id);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("{id}/rents")
    public ResponseEntity<List<Rent>> getRents(@PathVariable UUID id, @RequestParam(defaultValue = "true") boolean current) {
        return ResponseEntity.ok(rentManager.getByRealEstateID(id, current));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        realEstateManager.delete(id);

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody @Valid RealEstateRequest request) {
        RealEstate realEstate = request.toRealEstate();
        realEstate.setId(id);
        RealEstate result = realEstateManager.update(realEstate);
        return ResponseEntity.ok(result);
    }
}
