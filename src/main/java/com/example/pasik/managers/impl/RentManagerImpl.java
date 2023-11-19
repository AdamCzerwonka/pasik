package com.example.pasik.managers.impl;

import com.example.pasik.managers.RentManager;
import com.example.pasik.model.Client;
import com.example.pasik.model.RealEstate;
import com.example.pasik.model.Rent;
import com.example.pasik.repositories.ClientRepository;
import com.example.pasik.repositories.RealEstateRepository;
import com.example.pasik.repositories.RentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RentManagerImpl implements RentManager {
    private final RentRepository rentRepository;
    private final ClientRepository clientRepository;
    private final RealEstateRepository realEstateRepository;

    public RentManagerImpl(
            final RentRepository rentRepository,
            final ClientRepository clientRepository,
            final RealEstateRepository realEstateRepository) {
        this.rentRepository = rentRepository;
        this.clientRepository = clientRepository;
        this.realEstateRepository = realEstateRepository;
    }

    @Override
    public Rent create(UUID clientId, UUID realEstateId, LocalDate startDate) throws Exception {
        Client client = clientRepository.getById(clientId);
        Optional<RealEstate> realEstate = realEstateRepository.getById(realEstateId);

        if (realEstate.isEmpty()) {
            throw new RuntimeException("chuj");
        }

        //TODO: check if realEstate hasn't been rented already

        var rent = Rent
                .builder()
                .id(UUID.randomUUID())
                .client(client)
                .realEstate(realEstate.get())
                .startDate(startDate)
                .build();

        rentRepository.create(rent);
        return rent;
    }

    @Override
    public void endRent(UUID id) throws Exception {
        Optional<Rent> rentResult = rentRepository.getById(id);
        if (rentResult.isEmpty()) {
            throw new Exception("tes");
        }
        Rent rent = rentResult.get();

        rent.setEndDate(LocalDate.now());
        rentRepository.update(rent);
    }

    @Override
    public List<Rent> getByClientId(UUID clientId, boolean current) {
        return rentRepository.getByClientId(clientId, current);
    }
}
