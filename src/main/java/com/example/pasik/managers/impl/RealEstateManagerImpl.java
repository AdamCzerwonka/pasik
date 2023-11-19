package com.example.pasik.managers.impl;

import com.example.pasik.managers.RealEstateManager;
import com.example.pasik.model.RealEstate;
import com.example.pasik.model.Rent;
import com.example.pasik.repositories.RealEstateRepository;
import com.example.pasik.repositories.RentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RealEstateManagerImpl implements RealEstateManager {
    private final RealEstateRepository realEstateRepository;
    private final RentRepository rentRepository;

    public RealEstateManagerImpl(final RealEstateRepository realEstateRepository, final RentRepository rentRepository) {
        this.realEstateRepository = realEstateRepository;
        this.rentRepository = rentRepository;
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

    @Override
    public RealEstate update(RealEstate realEstate) {
        return realEstateRepository.update(realEstate);
    }

    @Override
    public void delete(UUID id) {
        List<Rent> rents = rentRepository.getByRealEstateId(id, true);
        if (!rents.isEmpty()) {
            throw new RuntimeException("Real estate is rented");
        }

        realEstateRepository.delete(id);
    }
}
