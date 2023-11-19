package com.example.pasik.model.dto.Rent;

import com.example.pasik.model.Rent;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentRequest {
    @NotNull(message = "ClientId cannot be null")
    private UUID clientId;
    @NotNull(message = "RealEstateId cannot be null")
    private UUID realEstateId;
    @FutureOrPresent(message = "Cannot create rent with past date")
    private LocalDate startDate;
}
