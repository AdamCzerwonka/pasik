package com.example.pasik.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public abstract class AbstractEntity {
    private UUID id;
}
