package com.demo.qa;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthCheckTest {

    @BeforeClass
    public void setup() {
        // Tells REST Assured where to send the requests
        RestAssured.baseURI = "https://hello-world-api-ajxj.onrender.com";
    }

    @Test
    public void verifyStatusEndpointReturns200() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/api/status") // Hits your developer endpoint
                .then()
                .statusCode(200)    // Asserts the response code is 200
                .body("status", equalTo("UP"))
                .body("role", equalTo("Harsha is a Developer"));
    }
}