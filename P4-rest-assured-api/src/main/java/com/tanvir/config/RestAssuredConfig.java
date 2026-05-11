package com.tanvir.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredConfig {

    private static final ConfigManager cfg = ConfigManager.getInstance();

    public static RequestSpecification buildRequestSpec() {
        return new RequestSpecBuilder()
            .setBaseUri(cfg.getBaseUrl())
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .addHeader("x-api-key", cfg.getApiKey())
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.BODY))
            .build();
    }

    public static ResponseSpecification buildResponseSpec() {
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .build();
    }

    public static void configure() {
        RestAssured.requestSpecification = buildRequestSpec();
        RestAssured.responseSpecification  = buildResponseSpec();
    }
}
