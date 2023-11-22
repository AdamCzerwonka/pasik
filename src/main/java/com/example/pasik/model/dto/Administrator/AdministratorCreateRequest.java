package com.example.pasik.model.dto.Administrator;

import com.example.pasik.model.Administrator;
import com.example.pasik.model.dto.User.UserCreateRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdministratorCreateRequest extends UserCreateRequest {
    @Builder
    public AdministratorCreateRequest(String firstName, String lastName, String login, Boolean active) {
        super(firstName, lastName, login, active);
    }

    public Administrator ToAdministrator() {
        return new Administrator(null, getFirstName(), getLastName(), getLogin(), getActive());
    }
}
