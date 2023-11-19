package com.example.pasik.model.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserUpdateRequest {
    private UUID id;
    private String firstName;
    private String lastName;
    private String login;
    private Boolean active;
}
