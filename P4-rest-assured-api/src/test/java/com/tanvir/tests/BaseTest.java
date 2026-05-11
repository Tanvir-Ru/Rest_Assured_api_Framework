package com.tanvir.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class BaseTest {

    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    protected static final String BASE_URL =
        System.getenv("BASE_URL") != null ? System.getenv("BASE_URL") : "https://reqres.in";

    @BeforeSuite
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .addHeader("x-api-key", System.getenv().getOrDefault("API_KEY", "reqres-free-v1"))
            .log(LogDetail.ALL)
            .build();

        responseSpec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .log(LogDetail.BODY)
            .build();

        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification  = responseSpec;

        System.out.println("✅ RestAssured configured. BaseURL: " + BASE_URL);
    }

    @AfterSuite
    public void tearDown() {
        System.out.println("✅ Test suite completed.");
    }
}
