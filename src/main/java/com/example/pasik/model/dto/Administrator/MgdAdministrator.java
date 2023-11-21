package com.example.pasik.model.dto.Administrator;

import com.example.pasik.model.Administrator;
import com.example.pasik.model.dto.User.MgdUser;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "administrator")
public class MgdAdministrator extends MgdUser {
    @BsonCreator
    public MgdAdministrator(@BsonId UUID id,
                     @BsonProperty(FIRST_NAME) String firstName,
                     @BsonProperty(LAST_NAME) String lastName,
                     @BsonProperty(LOGIN) String login,
                     @BsonProperty(ACTIVE) Boolean active) {
        super(id, firstName, lastName, login, active);
    }

    public static MgdAdministrator toMgdAdministrator(Administrator administrator) {
        return new MgdAdministrator(
                administrator.getId(), administrator.getFirstName(), administrator.getLastName(), administrator.getLogin(), administrator.getActive());
    }

    public Administrator toAdministrator() {
        return new Administrator(
                getId(),
                getFirstName(),
                getLastName(),
                getLogin(),
                getActive()
        );
    }

    public final static String ID = "_id";
    public final static String FIRST_NAME = "firstName";
    public final static String LAST_NAME = "lastName";
    public final static String LOGIN = "login";
    public final static String ACTIVE = "active";
}

