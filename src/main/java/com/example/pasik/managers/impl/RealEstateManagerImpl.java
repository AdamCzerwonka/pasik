package com.example.pasik.managers.impl;

import com.example.pasik.managers.RealEstateManager;
import com.example.pasik.model.RealEstate;
import com.example.pasik.repositories.RealEstateRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RealEstateManagerImpl implements RealEstateManager {
    private final RealEstateRepository realEstateRepository;

    public RealEstateManagerImpl(final RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }

    public RealEstate create(RealEstate realEstate) {
        return realEstateRepository.create(realEstate);
    }

    @Override
    public List<RealEstate> get() {
        return realEstateRepository.get();
    }

    @Override
    public RealEstate getById(UUID id) throws Exception {
        Optional<RealEstate> realEstate = realEstateRepository.getById(id);
        if (realEstate.isEmpty()) {
            throw new Exception("Real estate not found");
        }

        return realEstate.get();
    }
}
