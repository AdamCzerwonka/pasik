package com.example.pasik.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String login;
    private Boolean active;
    private String role;
}
