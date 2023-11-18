package com.example.pasik.model.dto.RealEstate;

import com.example.pasik.model.RealEstate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateRequest {
    private String name;
    private String address;
    private double area;
    private double price;

    public RealEstate toRealEstate() {
        return new RealEstate(null, name, address, area, price);
    }
}
