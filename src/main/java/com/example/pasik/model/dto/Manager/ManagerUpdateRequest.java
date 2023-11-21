package com.example.pasik.model.dto.Manager;

import com.example.pasik.model.Manager;
import com.example.pasik.model.dto.User.UserUpdateRequest;
import lombok.Builder;

import java.util.UUID;

public class ManagerUpdateRequest extends UserUpdateRequest {
    @Builder
    public ManagerUpdateRequest(UUID id, String firstName, String lastName, String login, Boolean active) {
        super(id, firstName, lastName, login, active);
    }

    public Manager ToManager() {
        return new Manager(getId(), getFirstName(), getLastName(), getLogin(), getActive());
    }
}
