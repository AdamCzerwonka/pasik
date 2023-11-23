package com.example.pasik.seeder;

import com.example.pasik.config.MongoClientConfiguration;
import com.example.pasik.managers.ClientManager;
import com.example.pasik.managers.RealEstateManager;
import com.example.pasik.managers.RentManager;
import com.example.pasik.model.Client;
import com.example.pasik.model.RealEstate;
import com.example.pasik.model.Rent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("dev")
public class LaunchSeeder implements CommandLineRunner {
    private final RealEstateManager realEstateManager;
    private final RentManager rentManager;
    private final ClientManager clientManager;

    public LaunchSeeder(final RealEstateManager realEstateManager, final RentManager rentManager, final ClientManager clientManager) {
        this.realEstateManager = realEstateManager;
        this.rentManager = rentManager;
        this.clientManager = clientManager;
    }

    @Override
    public void run(String... args) {
        loadUserData();
    }

    private void loadUserData() {
        Logger logger = LoggerFactory.getLogger(MongoClientConfiguration.class);
        try {
            Client client1 = new Client(null, "Carl", "Johnson", "CJ", true);
            client1 = clientManager.create(client1);
            Client client2 = new Client(null, "Neo", "Matrix", "neooo", true);
            client2 = clientManager.create(client2);
            Client inactiveClient = new Client(null, "Fiona", "Green", "shrek", false);
            inactiveClient = clientManager.create(inactiveClient);

            RealEstate realEstate1 = new RealEstate(null,"House", "Grove Street", 21, 15);
            realEstate1 = realEstateManager.create(realEstate1);
            RealEstate realEstate2 = new RealEstate(null,"Villa", "JumpStreet 21", 1500, 1000);
            realEstate2 = realEstateManager.create(realEstate2);

            Rent rent = rentManager.create(client1.getId(), realEstate1.getId(), LocalDate.now());
            logger.info("Data successfully initialized!");
        } catch (Exception e) {
            logger.error("Data initialization problem occurred!");
            throw new RuntimeException(e.getMessage());
        }

    }
}
