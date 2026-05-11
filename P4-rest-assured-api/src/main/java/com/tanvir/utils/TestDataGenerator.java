package com.tanvir.utils;

import net.datafaker.Faker;
import java.util.Locale;

/**
 * Generates realistic test data using DataFaker.
 */
public class TestDataGenerator {

    private static final Faker faker = new Faker(Locale.ENGLISH);

    public static String randomEmail() {
        return "qa_" + System.currentTimeMillis() + "@tanvir-test.com";
    }

    public static String randomFirstName() { return faker.name().firstName(); }
    public static String randomLastName()  { return faker.name().lastName(); }
    public static String randomJobTitle()  { return faker.job().title(); }

    public static String randomPhone()     { return faker.phoneNumber().phoneNumber(); }

    /** Returns a JSON body string for creating a user */
    public static String createUserJson() {
        return String.format(
            "{\"name\":\"%s %s\",\"job\":\"%s\"}",
            randomFirstName(), randomLastName(), randomJobTitle()
        );
    }

    /** Returns a JSON body string for registration */
    public static String registerJson(String email, String password) {
        return String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
    }
}
