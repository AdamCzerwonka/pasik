package com.example.pasik.model.dto.Administrator;

import com.example.pasik.model.Administrator;
import com.example.pasik.model.dto.User.UserUpdateRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AdministratorUpdateRequest extends UserUpdateRequest {
    @Builder
    public AdministratorUpdateRequest(UUID id, String firstName, String lastName, String login, Boolean active, String password) {
        super(id, firstName, lastName, login, active, password);
    }

    public Administrator ToAdministrator() {
        return new Administrator(getId(), getFirstName(), getLastName(), getLogin(), getActive(), getPassword());
    }
}
