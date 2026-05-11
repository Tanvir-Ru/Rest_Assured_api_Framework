package com.tanvir.tests;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseTest {

    // ── GET /api/users ───────────────────────────────────────────────────────

    @Test(groups = {"smoke", "regression"}, description = "GET all users returns 200 and data list")
    public void getAllUsers_returns200WithData() {
        given()
            .queryParam("page", 1)
        .when()
            .get("/api/users")
        .then()
            .statusCode(200)
            .body("page", equalTo(1))
            .body("data", not(empty()))
            .body("data[0].id", notNullValue())
            .body("data[0].email", containsString("@"));
    }

    @Test(groups = {"smoke"}, description = "GET single user by ID returns correct user")
    public void getUserById_returns200() {
        given()
            .pathParam("id", 2)
        .when()
            .get("/api/users/{id}")
        .then()
            .statusCode(200)
            .body("data.id", equalTo(2))
            .body("data.email", not(emptyString()))
            .body("data.first_name", not(emptyString()));
    }

    @Test(groups = {"regression"}, description = "GET non-existent user returns 404")
    public void getUserById_notFound_returns404() {
        given()
            .pathParam("id", 9999)
        .when()
            .get("/api/users/{id}")
        .then()
            .statusCode(404)
            .body(equalTo("{}"));
    }

    // ── POST /api/users ──────────────────────────────────────────────────────

    @Test(groups = {"smoke", "regression"}, description = "POST create user returns 201 with id")
    public void createUser_returns201WithId() {
        String body = """
            {
              "name": "Tanvir Hossain",
              "job": "Senior QA Engineer"
            }
            """;

        given()
            .body(body)
        .when()
            .post("/api/users")
        .then()
            .statusCode(201)
            .body("name", equalTo("Tanvir Hossain"))
            .body("job", equalTo("Senior QA Engineer"))
            .body("id", notNullValue())
            .body("createdAt", notNullValue());
    }

    @Test(dataProvider = "createUserData", groups = {"regression"})
    public void createUser_withVariousRoles(String name, String job) {
        String body = String.format("{\"name\":\"%s\",\"job\":\"%s\"}", name, job);

        given()
            .body(body)
        .when()
            .post("/api/users")
        .then()
            .statusCode(201)
            .body("name", equalTo(name))
            .body("job", equalTo(job));
    }

    @DataProvider(name = "createUserData")
    public Object[][] createUserData() {
        return new Object[][] {
            { "Alice Rahman",  "QA Lead" },
            { "Bob Islam",     "Junior QA" },
            { "Carol Chowdhury", "DevOps Engineer" }
        };
    }

    // ── PUT /api/users/{id} ──────────────────────────────────────────────────

    @Test(groups = {"regression"}, description = "PUT update user returns 200 with updatedAt")
    public void updateUser_returns200() {
        String body = "{\"name\":\"Tanvir Updated\",\"job\":\"Lead QA\"}";

        given()
            .pathParam("id", 2)
            .body(body)
        .when()
            .put("/api/users/{id}")
        .then()
            .statusCode(200)
            .body("name", equalTo("Tanvir Updated"))
            .body("updatedAt", notNullValue());
    }

    // ── DELETE /api/users/{id} ───────────────────────────────────────────────

    @Test(groups = {"regression"}, description = "DELETE user returns 204 No Content")
    public void deleteUser_returns204() {
        given()
            .pathParam("id", 2)
        .when()
            .delete("/api/users/{id}")
        .then()
            .statusCode(204);
    }

    // ── Response time ────────────────────────────────────────────────────────

    @Test(groups = {"performance"}, description = "GET users response under 2 seconds")
    public void getUsers_responseTimeUnder2Seconds() {
        Response response = when().get("/api/users");
        long time = response.getTime();
        assert time < 2000 : "Response time " + time + "ms exceeds 2000ms threshold";
    }
}
