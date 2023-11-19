package com.example.pasik.model.dto.Rent;

import com.example.pasik.model.Client;
import com.example.pasik.model.Rent;
import com.example.pasik.model.RealEstate;
import com.example.pasik.model.dto.Client.MgdClient;
import com.example.pasik.model.dto.RealEstate.MgdRealEstate;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MgdRent {
    @BsonCreator
    public MgdRent(@BsonId UUID id,
                   @BsonProperty MgdClient client,
                   @BsonProperty MgdRealEstate realEstate,
                   @BsonProperty LocalDate startDate,
                   @BsonProperty LocalDate endDate) {
        this.id = id;
        this.client = client;
        this.realEstate = realEstate;
        this.startDate= startDate;
        this.endDate = endDate;
    }
    @BsonId
    private UUID id;
    @BsonProperty("client")
    MgdClient client;
    @BsonProperty("realEstate")
    MgdRealEstate realEstate;
    @BsonProperty("startDate")
    LocalDate startDate;
    @BsonProperty("endDate")
    LocalDate endDate;

    public Rent toRent() {
        return new Rent(
                getId(),
                getClient().toClient(),
                getRealEstate().toRealEstate(),
                getStartDate(),
                getEndDate());
    }

    static public MgdRent toMgdRent(Rent rent) {
        return new MgdRent(
                rent.getId(),
                MgdClient.toMgdClient(rent.getClient()),
                MgdRealEstate.toMgdRealEstate(rent.getRealEstate()),
                rent.getStartDate(),
                rent.getEndDate());
    }
}
