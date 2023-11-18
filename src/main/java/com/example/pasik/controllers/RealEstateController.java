package com.example.pasik.controllers;

import com.example.pasik.managers.RealEstateManager;
import com.example.pasik.model.RealEstate;
import com.example.pasik.model.dto.RealEstate.RealEstateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/realestate")
public class RealEstateController {
    private final RealEstateManager realEstateManager;

    public RealEstateController(final RealEstateManager realEstateManager) {
        this.realEstateManager = realEstateManager;
    }

    @PostMapping
    public ResponseEntity<RealEstate> create(@RequestBody RealEstateRequest request) throws URISyntaxException {
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
}
