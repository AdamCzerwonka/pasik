package com.example.pasik.controllers;

import com.example.pasik.model.dto.RealEstate.RealEstateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RealEstateControllerTests {
    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
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

    @Test
    public void getAll() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/realestate")
                .then()
                .assertThat()
                .body("size()", is(0))
                .statusCode(200);
    }

    @Test
    public void get() {
        RealEstateRequest realEstateRequest = RealEstateRequest
                .builder()
                .name("test")
                .address("test")
                .area(10)
                .price(15)
                .build();

        String realEstateId = given()
                .contentType(ContentType.JSON)
                .body(realEstateRequest)
                .when()
                .post("/realestate")
                .then()
                .extract()
                .path("id");


        given()
                .contentType(ContentType.JSON)
                .pathParam("id", realEstateId)
                .when()
                .get("/realestate/{id}")
                .then()
                .assertThat()
                .body("name", Matchers.equalTo(realEstateRequest.getName()))
                .statusCode(200);
    }

    @Test
    public void getFail() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", UUID.randomUUID())
                .when()
                .get("/realestate/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
