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
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
    public void testCreateShouldSuccessWhenPassingCorrectData() {
        var realEstate = RealEstateRequest
                .builder()
                .name("test")
                .address("test")
                .price(10.1)
                .area(5.5)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(realEstate)
                .when()
                .post("/realestate")
                .then()
                .assertThat()
                .body("id", Matchers.notNullValue())
                .statusCode(201);
    }

    @Test
    public void testGetAllShouldReturnAllExistingRealEstates() {
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
    public void getByIdShouldFail() {
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
