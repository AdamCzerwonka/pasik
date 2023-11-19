package com.example.pasik.model.dto.Client;

import com.example.pasik.model.Client;
import com.example.pasik.model.dto.User.UserUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientUpdateRequest extends UserUpdateRequest {
    public Client ToClient() {
        return new Client(getId(), getFirstName(), getLastName(), getLogin(), getActive());
    }
}
