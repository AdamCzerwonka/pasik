package com.example.pasik.managers;

import com.example.pasik.model.Rent;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentManager {
    Rent create(UUID clientId, UUID realEstateId, LocalDate startDate) throws Exception;

    void endRent(UUID id) throws Exception;

    List<Rent> getByClientId(UUID clientId, boolean current);

    List<Rent> getByRealEstateID(UUID realEstateId, boolean current);
}
