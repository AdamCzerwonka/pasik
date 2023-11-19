package com.example.pasik.managers;

import com.example.pasik.model.Rent;

import java.time.LocalDate;
import java.util.UUID;

public interface RentManager {
    Rent create(UUID clientId, UUID realEstateId, LocalDate startDate) throws Exception;
    void endRent(UUID id) throws Exception;
}
