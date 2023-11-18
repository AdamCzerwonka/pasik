package com.example.pasik.model.dto.Client;

import com.example.pasik.model.Client;
import com.example.pasik.model.dto.User.UserCreateRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ClientCreateRequest extends UserCreateRequest {
    public Client ToClient() {
        return new Client(null, getFirstName(), getLastName(), getLogin(), getActive());
    }
}
