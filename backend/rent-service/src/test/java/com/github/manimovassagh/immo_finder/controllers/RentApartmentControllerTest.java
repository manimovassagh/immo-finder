package com.github.manimovassagh.immo_finder.controllers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class RentApartmentControllerTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @LocalServerPort
    private Integer port;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void shouldCreateApartmentWithMinimalFields() {
        // given
        String requestBody = """
            {
                "title": "Test Apartment",
                "basePrice": 800.00,
                "additionalCosts": 150.00,
                "rooms": 1,
                "address": {
                    "street": "First Test Street",
                    "houseNumber": "42A",
                    "postalCode": "12345",
                    "city": "Test City"
                }
            }
            """;

        // when/then
        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/api/apartments")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("title", org.hamcrest.Matchers.equalTo("Test Apartment"))
            .body("basePrice", org.hamcrest.Matchers.equalTo(800.00f))
            .body("rooms", org.hamcrest.Matchers.equalTo(1));
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsMissing() {
        // given
        String requestBody = """
            {
                "basePrice": 800.00,
                "additionalCosts": 150.00,
                "rooms": 1,
                "address": {
                    "street": "Second Test Street",
                    "houseNumber": "43B",
                    "postalCode": "12345",
                    "city": "Test City"
                }
            }
            """;

        // when/then
        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/api/apartments")
        .then()
            .statusCode(400)
            .body("errors", notNullValue());
    }
}
