package com.example.pasik.model.dto.Client;

import com.example.pasik.model.Client;
import com.example.pasik.model.dto.User.MgdUser;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "client")
public class MgdClient extends MgdUser {
    @BsonCreator
    public MgdClient(@BsonId UUID id,
                     @BsonProperty("firstName") String firstName,
                     @BsonProperty("lastName") String lastName,
                     @BsonProperty("login") String login,
                     @BsonProperty("active") Boolean active) {
        super(id, firstName, lastName, login, active);
    }

    public static MgdClient toMgdClient(Client client) {
        return new MgdClient(
                client.getId(),client.getFirstName(),client.getLastName(),client.getLogin(),client.getActive());
    }

    public Client toClient() {
        return new Client(
                getId(),
                getFirstName(),
                getLastName(),
                getLogin(),
                getActive()
        );
    }
}
