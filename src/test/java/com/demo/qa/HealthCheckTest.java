package com.demo.qa;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthCheckTest {

    @BeforeSuite
    public void wakeUpEnvironment() throws InterruptedException {
        RestAssured.baseURI = "https://YOUR-RENDER-URL-HERE.onrender.com";

        System.out.println("Pinging environment to wake it up...");
        int maxRetries = 3;

        for (int i = 0; i < maxRetries; i++) {
            try {
                // Try to hit the endpoint
                int statusCode = RestAssured.get("/api/status").statusCode();
                if (statusCode == 200) {
                    System.out.println("Environment is awake and ready!");
                    return; // Exit the loop and proceed to tests
                }
            } catch (Exception e) {
                System.out.println("Connection refused, server is likely sleeping.");
            }

            System.out.println("Waiting 60 seconds for cold start... (Attempt " + (i+1) + " of " + maxRetries + ")");
            Thread.sleep(60000); // Pause execution for 60 seconds
        }
        System.out.println("Warning: Environment did not wake up after " + maxRetries + " attempts.");
    }

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://hello-world-api-ajxj.onrender.com";
    }

    @Test
    public void verifyStatusEndpointReturns200() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/api/status")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }
}