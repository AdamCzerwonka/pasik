package com.example.pasik.model.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String login;
    private Boolean active;
}
