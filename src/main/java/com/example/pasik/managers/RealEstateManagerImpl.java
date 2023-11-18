package com.example.pasik.managers;

import com.example.pasik.model.RealEstate;
import com.example.pasik.repositories.RealEstateRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
