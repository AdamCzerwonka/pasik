package com.example.pasik.model.dto.Administrator;

import com.example.pasik.model.Administrator;
import com.example.pasik.model.dto.User.UserUpdateRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdministratorUpdateRequest extends UserUpdateRequest {
    @Builder
    public AdministratorUpdateRequest(UUID id, String firstName, String lastName, String login, Boolean active) {
        super(id, firstName, lastName, login, active);
    }

    public Administrator ToAdministrator() {
        return new Administrator(getId(), getFirstName(), getLastName(), getLogin(), getActive());
    }
}