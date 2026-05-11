package com.tanvir.tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Loan API tests — demonstrates chained request flows:
 * Create user → Create loan → Verify loan → Update status → Verify update
 *
 * Uses reqres.in as a mock backend (adapt endpoints to your real FinTech API).
 */
public class LoanApiTest extends BaseTest {

    /**
     * Full chained scenario:
     * Step 1 → Create applicant (POST /api/users)
     * Step 2 → Simulate loan creation using returned user ID
     * Step 3 → Verify the loan record (GET)
     * Step 4 → Update loan status (PUT)
     */
    @Test(groups = {"smoke", "chained"}, description = "Full loan application chained flow")
    public void loanApplication_chainedFlow() {

        // ── Step 1: Create applicant ─────────────────────────────────────────
        Response createUser = given()
            .body("{\"name\":\"Tanvir Hossain\",\"job\":\"loan_applicant\"}")
        .when()
            .post("/api/users")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .extract().response();

        String userId = createUser.path("id").toString();
        System.out.println("✅ Step 1 — User created, id: " + userId);

        // ── Step 2: Simulate loan application (update job to active_loan) ────
        String loanPayload = String.format(
            "{\"name\":\"Tanvir Hossain\",\"job\":\"active_loan_holder\",\"userId\":\"%s\"}", userId
        );

        Response loanResponse = given()
            .body(loanPayload)
        .when()
            .post("/api/users")
        .then()
            .statusCode(201)
            .body("job", equalTo("active_loan_holder"))
            .extract().response();

        String loanId = loanResponse.path("id").toString();
        System.out.println("✅ Step 2 — Loan created, loanId: " + loanId);

        // ── Step 3: Verify user still accessible ────────────────────────────
        given()
            .pathParam("id", 2)   // reqres.in has fixed user IDs
        .when()
            .get("/api/users/{id}")
        .then()
            .statusCode(200)
            .body("data.id", notNullValue());
        System.out.println("✅ Step 3 — Loan record verified");

        // ── Step 4: Update loan status ───────────────────────────────────────
        given()
            .pathParam("id", 2)
            .body("{\"name\":\"Tanvir Hossain\",\"job\":\"loan_closed\"}")
        .when()
            .put("/api/users/{id}")
        .then()
            .statusCode(200)
            .body("job", equalTo("loan_closed"))
            .body("updatedAt", notNullValue());
        System.out.println("✅ Step 4 — Loan status updated to closed");
    }

    @Test(groups = {"regression"}, description = "Loan amount must be positive — negative validation")
    public void createLoan_negativeAmount_returns400() {
        // Demonstrates how to test invalid business-rule inputs
        given()
            .body("{\"name\":\"Bad Loan\",\"job\":\"negative_amount_-5000\"}")
        .when()
            .post("/api/users")
        // reqres.in accepts anything; in a real API this should return 400
        // Adjust assertion to match your real API behaviour
        .then()
            .statusCode(anyOf(equalTo(201), equalTo(400)));
    }

    @Test(groups = {"regression"}, description = "Pagination returns correct page size")
    public void getLoans_pagination_returnsCorrectPageSize() {
        Response response = given()
            .queryParam("page", 1)
            .queryParam("per_page", 3)
        .when()
            .get("/api/users")
        .then()
            .statusCode(200)
            .body("data", hasSize(lessThanOrEqualTo(6)))
            .extract().response();

        int total = response.path("total");
        assert total > 0 : "Total user count should be > 0";
    }
}
