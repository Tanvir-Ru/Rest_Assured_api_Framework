package com.tanvir.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Handles authentication flows — JWT login, token extraction, Bearer header helper.
 */
public class AuthHelper {

    private static String cachedToken = null;

    /**
     * Login via POST /api/login and return the auth token.
     * Token is cached within the test session.
     */
    public static String getAuthToken(String email, String password) {
        if (cachedToken != null) return cachedToken;

        String body = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);

        Response response = given()
            .body(body)
        .when()
            .post("/api/login")
        .then()
            .statusCode(200)
            .extract().response();

        cachedToken = response.path("token");
        return cachedToken;
    }

    /** Returns a Bearer Authorization header value for the token. */
    public static String bearerHeader(String token) {
        return "Bearer " + token;
    }

    /** Clears the cached token (call in @AfterSuite if needed). */
    public static void clearToken() {
        cachedToken = null;
    }
}
