package com.example.pasik.seeder;

import com.example.pasik.managers.ClientManager;
import com.example.pasik.managers.RealEstateManager;
import com.example.pasik.managers.RentManager;
import com.example.pasik.model.Client;
import com.example.pasik.model.RealEstate;
import com.example.pasik.model.Rent;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@Profile("test")
public class TestDataSeeder {
    private final RealEstateManager realEstateManager;
    private final RentManager rentManager;
    private final ClientManager clientManager;

    private static final List<UUID> realEstateIds = new ArrayList<>();
    private static final List<UUID> rentIds = new ArrayList<>();
    private static final List<UUID> clientIds = new ArrayList<>();

    public TestDataSeeder(final RealEstateManager realEstateManager, final RentManager rentManager, final ClientManager clientManager) {
        this.realEstateManager = realEstateManager;
        this.rentManager = rentManager;
        this.clientManager = clientManager;
    }

    @Bean
    public CommandLineRunner initTestData() {
        return args -> {
            Client testClient = new Client(null, "firstName", "lastName", "login", true);
            testClient = clientManager.create(testClient);
            Client testClient2 = new Client(null, "firstName2", "lastName2", "login2", true);
            testClient2 = clientManager.create(testClient2);
            Client inactiveClient = new Client(null, "firstNameInactive", "lastNameInactive", "loginInactive", false);
            inactiveClient = clientManager.create(inactiveClient);

            clientIds.add(testClient.getId());
            clientIds.add(testClient2.getId());
            clientIds.add(inactiveClient.getId());

            RealEstate testRealEstate = new RealEstate(null,"name", "address", 15, 15);
            testRealEstate = realEstateManager.create(testRealEstate);
            RealEstate testRealEstate2 = new RealEstate(null,"name2", "address2", 21, 21);
            testRealEstate2 = realEstateManager.create(testRealEstate2);

            realEstateIds.add(testRealEstate.getId());
            realEstateIds.add(testRealEstate2.getId());

            Rent rent = rentManager.create(testClient.getId(), testRealEstate.getId(), LocalDate.now());

            rentIds.add(rent.getId());
        };
    }

    public static List<UUID> getRealEstateIds() {
        return Collections.unmodifiableList(realEstateIds);
    }

    public static List<UUID> getRentIds() {
        return Collections.unmodifiableList(rentIds);
    }

    public static List<UUID> getClientIds() {
        return Collections.unmodifiableList(clientIds);
    }
}
