package com.example.pasik.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RealEstateControllerTests {
    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeAll
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void createRealEstate() throws JSONException {
        JSONObject newRealEstate = new JSONObject();
        newRealEstate.put("name", "test");
        newRealEstate.put("address", "test");
        newRealEstate.put("price", 10.1);
        newRealEstate.put("area", 5.5);

        given()
                .contentType(ContentType.JSON)
                .body(newRealEstate.toString())
                .when()
                .post("/realestate")
                .then()
                .assertThat()
                .statusCode(201);
    }

}
