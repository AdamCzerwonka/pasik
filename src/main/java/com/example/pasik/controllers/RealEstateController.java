package com.example.pasik.controllers;

import com.example.pasik.managers.RealEstateManager;
import com.example.pasik.model.RealEstate;
import com.example.pasik.model.dto.RealEstate.RealEstateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/realestate")
public class RealEstateController {
    private final RealEstateManager realEstateManager;

    public RealEstateController(final RealEstateManager realEstateManager) {
        this.realEstateManager = realEstateManager;
    }

    @PostMapping
    public RealEstate create(@RequestBody RealEstateRequest request) {
        var result = realEstateManager.create(request.toRealEstate());
        return result;
    }

    @GetMapping
    public List<RealEstate> get() {
        return realEstateManager.get();
    }
}
