package com.example.pasik.model.dto.Client;

import com.example.pasik.model.Client;
import com.example.pasik.model.dto.User.UserCreateRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientCreateRequest extends UserCreateRequest {
    @Builder
    public ClientCreateRequest(String firstName, String lastName, String login, Boolean active) {
        super(firstName, lastName, login, active);
    }

    public Client ToClient() {
        return new Client(null, getFirstName(), getLastName(), getLogin(), getActive());
    }
}
