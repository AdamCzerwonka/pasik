package com.example.pasik.model.mongo;

import com.example.pasik.model.RealEstate;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Data
public class MgdRealEstate {
    @BsonCreator
    public MgdRealEstate(
            @BsonId UUID id,
            @BsonProperty("name") String name,
            @BsonProperty("address") String address,
            @BsonProperty("area") double area,
            @BsonProperty("price") double price) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.area = area;
        this.price = price;
    }

    public static MgdRealEstate toMgdRealEstate(RealEstate realEstate) {
        return new MgdRealEstate(
                realEstate.getId(),
                realEstate.getName(),
                realEstate.getAddress(),
                realEstate.getArea(),
                realEstate.getPrice());
    }

    public RealEstate toRealEstate() {
        return new RealEstate(
                getId(),
                getName(),
                getAddress(),
                getArea(),
                getPrice()
        );
    }

    @BsonId
    private UUID id;
    @BsonProperty("name")
    private String name;
    @BsonProperty("address")
    private String address;
    @BsonProperty("area")
    private double area;
    @BsonProperty("price")
    private double price;
}
