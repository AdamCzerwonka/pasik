package com.example.pasik.repositories;

import com.example.pasik.model.Rent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentRepository {
    List<Rent> get();
    Optional<Rent> getById(UUID id);
    Rent create(Rent rent);
    Rent update(Rent rent);
    void delete(UUID id);
}
