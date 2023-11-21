package com.example.pasik.model.dto.Manager;

import com.example.pasik.model.Client;
import com.example.pasik.model.Manager;
import com.example.pasik.model.dto.User.UserCreateRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagerCreateRequest extends UserCreateRequest {
    @Builder
    public ManagerCreateRequest(String firstName, String lastName, String login, Boolean active) {
        super(firstName, lastName, login, active);
    }

    public Manager ToManager() {
        return new Manager(null, getFirstName(), getLastName(), getLogin(), getActive());
    }
}