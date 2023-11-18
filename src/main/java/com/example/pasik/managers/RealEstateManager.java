package com.example.pasik.managers;

import com.example.pasik.model.RealEstate;

import java.util.List;

public interface RealEstateManager {
    RealEstate create(RealEstate realEstate);
    List<RealEstate> get();
}
