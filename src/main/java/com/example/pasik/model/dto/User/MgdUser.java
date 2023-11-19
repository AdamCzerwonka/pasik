package com.example.pasik.model.dto.User;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;
@Data
@BsonDiscriminator(key = "_clazz", value = "user")
public abstract class MgdUser {
    @BsonCreator
    public MgdUser(@BsonId UUID id,
                   @BsonProperty("firstName") String firstName,
                   @BsonProperty("lastName") String lastName,
                   @BsonProperty("login") String login,
                   @BsonProperty("active") Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.active = active;
    }
    @BsonId
    private UUID id;
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("lastName")
    private String lastName;
    @BsonProperty("login")
    private String login;
    @BsonProperty("active")
    private Boolean active;


}
