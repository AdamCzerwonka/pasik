package com.example.pasik.repositories;

import com.example.pasik.model.Rent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentRepository {
    List<Rent> get() throws Exception;
    Rent getById(UUID id) throws Exception;
    Rent create(Rent rent) throws Exception;
    Rent update(Rent rent);
    void delete(UUID id) throws Exception;
    void endRent(UUID id) throws Exception;
}
