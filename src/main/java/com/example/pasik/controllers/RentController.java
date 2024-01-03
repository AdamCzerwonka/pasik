package com.example.pasik.controllers;

import com.example.pasik.exceptions.*;
import com.example.pasik.managers.RentManager;
import com.example.pasik.model.Error;
import com.example.pasik.model.Rent;
import com.example.pasik.model.dto.Rent.RentCreateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rent")
public class RentController {
    private final RentManager rentManager;

    public RentController(final RentManager rentManager) {
        this.rentManager = rentManager;
    }

    @PostMapping
    public ResponseEntity<Rent> create(@RequestBody @Valid RentCreateRequest rentRequest) throws NotFoundException
            , URISyntaxException, RealEstateRentedException, AccountInactiveException {
        var result = rentManager.create(
                rentRequest.getClientId(),
                rentRequest.getRealEstateId(),
                rentRequest.getStartDate());

        return ResponseEntity.created(new URI("http://localhost:8080/rent/" + result.getId())).body(result);
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<?> endRent(@PathVariable UUID id) throws NotFoundException, RentEndedException, InvalidEndRentDateException {
        rentManager.endRent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Rent>> get() {
        return ResponseEntity.ok(rentManager.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws NotFoundException {
        Rent result = rentManager.getById(id);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            rentManager.delete(id);

            return ResponseEntity.ok("Deleted");
        } catch (RentEndedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RealEstateRentedException.class)
    public Error handleRealEstateRented(RealEstateRentedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("realEstateRented", ex.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountInactiveException.class)
    public Error handleAccountInactive(AccountInactiveException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("accountInactive", ex.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RentEndedException.class)
    public Error handleRentEnded(RentEndedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("rentEnded", ex.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEndRentDateException.class)
    public Error handleInvalidEndRentDate(InvalidEndRentDateException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("invalidEndDate", ex.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.value(), errors);
    }
}
