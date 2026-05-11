package com.tanvir.tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthApiTest extends BaseTest {

    @Test(groups = {"smoke", "auth"}, description = "Login with valid credentials returns token")
    public void login_validCredentials_returnsToken() {
        String body = """
            {
              "email": "eve.holt@reqres.in",
              "password": "cityslicka"
            }
            """;

        given()
            .body(body)
        .when()
            .post("/api/login")
        .then()
            .statusCode(200)
            .body("token", notNullValue())
            .body("token", not(emptyString()));
    }

    @Test(groups = {"auth", "negative"}, description = "Login without password returns 400 with error")
    public void login_missingPassword_returns400() {
        String body = "{\"email\": \"peter@klaven.com\"}";

        given()
            .body(body)
        .when()
            .post("/api/login")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));
    }

    @Test(groups = {"auth", "negative"}, description = "Login with invalid email returns 400")
    public void login_invalidCredentials_returns400() {
        String body = """
            {
              "email": "nonexistent@fake.com",
              "password": "wrongpass"
            }
            """;

        given()
            .body(body)
        .when()
            .post("/api/login")
        .then()
            .statusCode(400)
            .body("error", notNullValue());
    }

    @Test(groups = {"auth"}, description = "Token is extracted and reused in subsequent request")
    public void login_tokenUsedInSubsequentRequest() {
        // Step 1: Login and extract token
        String token = given()
            .body("{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}")
        .when()
            .post("/api/login")
        .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Step 2: Use token in next request header
        assert token != null && !token.isEmpty() : "Token should not be empty";

        given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get("/api/users")
        .then()
            .statusCode(200);
    }
}
