package com.example.pasik.controllers;

import com.example.pasik.seeder.TestDataSeeder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RentControllerTests {
    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() throws JSONException {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void givenUrl_whenGet_thenReturnALLRents() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/rent")
                .then()
                .assertThat()
                .body("size()", is(1))
                .statusCode(200);
    }

    @Test
    public void givenUrl_whenGetById_getFail() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", UUID.randomUUID())
                .when()
                .get("/rent/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void givenRentId_whenGetById_thenReturnRent() {
        List<UUID> rentIds = TestDataSeeder.getRentIds();
        UUID rentId = rentIds.get(0);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", rentId)
                .when()
                .get("/rent/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(rentId.toString()))
                .body("client", notNullValue())
                .body("realEstate", notNullValue())
                .body("startDate", notNullValue());
    }
}
