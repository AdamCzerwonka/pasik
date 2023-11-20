package com.example.pasik.controllers;

import com.example.pasik.model.dto.Client.ClientCreateRequest;
import com.example.pasik.model.dto.Client.ClientUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientControllerTests {
    private final static String BASE_URI = "http://localhost/client";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void testCreateShouldPassWhenAddingCorrectData() {
        ClientCreateRequest clientCreteRequest = ClientCreateRequest
                .builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .login("testLogin1")
                .active(true)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void testCreateShouldFailWhenAddingIncorrectData() throws JSONException {
        ClientCreateRequest clientCreteRequest = ClientCreateRequest
                .builder()
                .firstName("")
                .lastName("")
                .login("testLogin1")
                .active(true)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void testCreateShouldFailWhenDuplicatingLogin() throws JSONException {
        ClientCreateRequest clientCreteRequest1 = ClientCreateRequest
                .builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .login("testLogin1")
                .active(true)
                .build();

        ClientCreateRequest clientCreteRequest2 = ClientCreateRequest
                .builder()
                .firstName("TestFirstName2")
                .lastName("TestLastName2")
                .login("testLogin1")
                .active(false)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest1)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest2)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void testGetShouldReturnCorrectAmountOfData() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(404);

        ClientCreateRequest clientCreteRequest = ClientCreateRequest
                .builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .login("testLogin1")
                .active(true)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .assertThat()
                .body("size()", is(1))
                .statusCode(200);
    }

    @Test
    public void testGetByIdShouldReturnCorrectData() {
        ClientCreateRequest clientCreteRequest = ClientCreateRequest
                .builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .login("testLogin1")
                .active(true)
                .build();

        String id = given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("id");

        given()
                .contentType(ContentType.JSON)
                .get("/" + id)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("firstName", equalTo(clientCreteRequest.getFirstName()))
                .body("lastName", equalTo(clientCreteRequest.getLastName()))
                .body("login", equalTo(clientCreteRequest.getLogin()))
                .body("active", equalTo(clientCreteRequest.getActive()));
    }

    @Test
    public void testGetByIdShouldFailWhenPassingRandomId() {
        given()
                .contentType(ContentType.JSON)
                .get("/" + UUID.randomUUID())
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void testFindClientsByLoginShouldReturnCorrectAmountOfData() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/login/many/test")
                .then()
                .assertThat()
                .statusCode(404);

        ClientCreateRequest clientCreteRequest = ClientCreateRequest
                .builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .login("testLogin1")
                .active(true)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/login/many/test")
                .then()
                .assertThat()
                .body("size()", is(1))
                .statusCode(200);
    }

    @Test
    public void testGetByLoginShouldReturnCorrectData() {
        ClientCreateRequest clientCreteRequest = ClientCreateRequest
                .builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .login("testLogin1")
                .active(true)
                .build();

        String id = given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("id");

        given()
                .contentType(ContentType.JSON)
                .get("/login/single/" + clientCreteRequest.getLogin())
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("firstName", equalTo(clientCreteRequest.getFirstName()))
                .body("lastName", equalTo(clientCreteRequest.getLastName()))
                .body("login", equalTo(clientCreteRequest.getLogin()))
                .body("active", equalTo(clientCreteRequest.getActive()));
    }

    @Test
    public void testGetByLoginShouldFailWhenPassingRandomId() {
        given()
                .contentType(ContentType.JSON)
                .get("/login/single/abc123")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void testUpdateShouldPassWhenPassingCorrectData() {
        ClientCreateRequest clientCreteRequest = ClientCreateRequest
                .builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .login("testLogin1")
                .active(true)
                .build();

        String id = given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("id")
                .toString();

        ClientUpdateRequest clientUpdateRequest = ClientUpdateRequest
                .builder()
                .id(UUID.fromString(id))
                .firstName("NewTestFirstName")
                .lastName("NewTestLastName")
                .login("NewTestLogin")
                .active(false)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(clientUpdateRequest)
                .when()
                .put()
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("firstName", equalTo(clientUpdateRequest.getFirstName()))
                .body("lastName", equalTo(clientUpdateRequest.getLastName()))
                .body("login", equalTo(clientCreteRequest.getLogin()))
                .body("active", equalTo(clientUpdateRequest.getActive()));
    }

    @Test
    public void testUpdateShouldFailWhenPassingIncorrectData() throws JSONException {
        ClientCreateRequest clientCreteRequest = ClientCreateRequest
                .builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .login("testLogin1")
                .active(true)
                .build();

        String id = given()
                .contentType(ContentType.JSON)
                .body(clientCreteRequest)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("id")
                .toString();

        ClientUpdateRequest clientUpdateRequest = ClientUpdateRequest
                .builder()
                .id(UUID.fromString(id))
                .firstName("")
                .lastName("")
                .login("NewTestLogin")
                .active(false)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(clientUpdateRequest)
                .when()
                .put()
                .then()
                .assertThat()
                .statusCode(400);
    }
}
